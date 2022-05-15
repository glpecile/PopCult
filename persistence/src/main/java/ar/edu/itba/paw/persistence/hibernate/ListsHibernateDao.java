package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.persistence.hibernate.utils.PaginationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Repository
public class ListsHibernateDao implements ListsDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListsHibernateDao.class);

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return Optional.ofNullable(em.find(MediaList.class, mediaListId));
    }

    @Override
    public PageContainer<MediaList> getAllLists(int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(mediaListId) FROM MediaList");
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getMediaListByUser(User user, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist WHERE userid = :userid OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("userid", user.getUserId());
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM MediaList m WHERE user = :user")
                .setParameter("user", user);
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getPublicMediaListByUser(User user, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist WHERE userid = :userid AND visibility = :visibility OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("userid", user);
        nativeQuery.setParameter("visibility", true);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(m.mediaListId) FROM MediaList m WHERE user = :user  AND m.visible = :visibility")
                .setParameter("user", user).setParameter("visibility", true);
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    private List<MediaList> getMediaLists(List<Long> listIds) {
        final TypedQuery<MediaList> query = em.createQuery("FROM MediaList WHERE mediaListId IN (:listIds)", MediaList.class)
                .setParameter("listIds", listIds);
        return listIds.isEmpty() ? Collections.emptyList() : query.getResultList();
    }

    @Override
    public PageContainer<Media> getMediaInList(MediaList mediaList, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM listelement WHERE medialistid = :mediaListId OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("mediaListId", mediaList.getMediaListId());
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) FROM listelement WHERE medialistid = :mediaListId")
                .setParameter("mediaListId", mediaList.getMediaListId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        return new PageContainer<>(getMedias(mediaIds), page, pageSize, count);
    }

    private List<Media> getMedias(List<Long> mediaIds) {
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in :mediaIds", Media.class);
        query.setParameter("mediaIds", mediaIds);
        return mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();
    }

    @Override
    public PageContainer<MediaList> getLastAddedLists(int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM medialist WHERE visibility = :visibility ORDER BY creationDate DESC OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("visibility", true);
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(mediaListId) FROM MediaList WHERE visible = :visibility")
                .setParameter("visibility", true);
        long count = (long) countQuery.getSingleResult();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    private Query buildAndWhereStatement(String baseQuery, Integer page, Integer pageSize, String term, boolean visibility, SortType sort, List<Genre> genre, int minMatches, LocalDateTime fromDate, LocalDateTime toDate) {
        StringBuilder toReturn = new StringBuilder();
        final Map<String, Object> parameters = new HashMap<>();
        toReturn.append(baseQuery);
        LinkedList<String> where = new LinkedList<>();
        LinkedList<String> groupBy = new LinkedList<>();
        LinkedList<String> having = new LinkedList<>();

        if (term != null) {
            where.add(SortType.TITLE.getNameMediaList() + " ILIKE CONCAT('%', :listname, '%')");
            parameters.put("listname", term);
        }
        if (!genre.isEmpty()) {

            where.add(" genreid IN ( :genres) ");
            parameters.put("genres", genre.stream().map(Genre::ordinal).collect(Collectors.toList()));
            groupBy.add(" medialist.medialistid ");
            groupBy.add(" visibility ");
            if (sort != null && sort != SortType.POPULARITY)
                groupBy.add(sort.getNameMediaList());
            if (fromDate != null && toDate != null && !groupBy.contains(SortType.DATE.getNameMediaList())) {
                groupBy.add(SortType.DATE.getNameMediaList());
            }
            having.add(" COUNT(mediaId) >= :minMatches ");
            parameters.put("minMatches", minMatches);
        }
        if(sort == SortType.POPULARITY){
            groupBy.add(" medialist.medialistid");
        }
        where.add(" visibility = :visibility ");
        parameters.put("visibility", visibility);

        if (fromDate != null && toDate != null) {
            where.add(" creationdate BETWEEN :fromDate AND :toDate ");
            parameters.put("fromDate", fromDate.toLocalDate());
            parameters.put("toDate", toDate.toLocalDate());
        }
        if (!where.isEmpty()) {
            toReturn.append("WHERE ");
            toReturn.append(where.removeFirst());
            where.forEach(w -> toReturn.append(" AND ").append(w));
        }

        if (!groupBy.isEmpty()) {
            toReturn.append(" GROUP BY ");
            toReturn.append(groupBy.removeFirst());
            groupBy.forEach(w -> toReturn.append(" , ").append(w));
        }
        if (!having.isEmpty()) {
            toReturn.append(" HAVING ");
            toReturn.append(having.removeFirst());
            having.forEach(w -> toReturn.append(" AND ").append(w));
        }
        if (sort != null) {
            toReturn.append(" ORDER BY ");
            if (sort == SortType.TITLE)
                toReturn.append(" LOWER(").append(sort.getNameMediaList()).append(") ");
            else
                toReturn.append(sort.getNameMediaList()).append(" DESC");
        }

        if (page != null && pageSize != null) {
            toReturn.append(" OFFSET :offset LIMIT :limit ");
            parameters.put("offset", (page - 1) * pageSize);
            parameters.put("limit", pageSize);
        }
        toReturn.append(" ) AS aux");
        final Query nativeQuery = em.createNativeQuery(toReturn.toString());
        parameters.forEach(nativeQuery::setParameter);
        return nativeQuery;
    }

    @Override
    public PageContainer<MediaList> getMediaListByFilters(int page, int pageSize, SortType sort, List<Genre> genre, int minMatches, LocalDateTime fromDate, LocalDateTime toDate, String term) {
        //Para paginacion
        //Pedimos el contenido paginado.
        PaginationValidator.validate(page,pageSize);

        String sortBaseString = "";
        String sortCountString = "";
        StringBuilder fromTables = new StringBuilder();
        fromTables.append( "mediaGenre NATURAL JOIN listelement NATURAL JOIN mediaList ");
        if (sort != null) {
            if (sort == SortType.TITLE) {
                sortBaseString = ", LOWER(" + sort.getNameMediaList() + ") ";
                sortCountString = "order by lower(" + sort.getNameMediaList() + ")";
            } else{
                sortBaseString = ", " + sort.getNameMediaList();

                if(sort == SortType.POPULARITY){
                    fromTables.append( "LEFT JOIN favoritelists ON medialist.medialistid = favoritelists.medialistid " );
                    sortCountString = "order by likes DESC ";
                }else{
                    sortCountString = "order by " + sort.getNameMediaList();
                }

            }
        }
        final String baseQuery = "SELECT medialistid FROM (SELECT DISTINCT medialist.medialistid " + sortBaseString + " FROM  " + fromTables;
        final Query nativeQuery = buildAndWhereStatement(baseQuery, page, pageSize, term, true, sort, genre, minMatches, fromDate, toDate);
        @SuppressWarnings("unchecked")
        List<Long> mediaListIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final String countBaseQuery = "SELECT COUNT(medialistid) FROM (SELECT DISTINCT medialist.medialistid FROM  " + fromTables;
        final Query countQuery = buildAndWhereStatement(countBaseQuery, null, null, term, true, null, genre, minMatches, fromDate, toDate);
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<MediaList> query = em.createQuery("from MediaList where mediaListId in (:mediaListIds) " + sortCountString, MediaList.class);
        query.setParameter("mediaListIds", mediaListIds);
        List<MediaList> mediaList = mediaListIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT medialistid FROM listelement WHERE mediaid = :mediaid OFFSET (:offset) LIMIT (:limit)");
        nativeQuery.setParameter("mediaid", media.getMediaId());
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = nativeQuery.getResultList();

        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaListId) FROM listelement WHERE mediaid = :mediaId")
                .setParameter("mediaId", media.getMediaId());
        long count = ((Number) countQuery.getSingleResult()).longValue();

        List<MediaList> list = getMediaLists(listIds);

        return new PageContainer<>(list, page, pageSize, count);
    }

    @Override
    public MediaList createMediaList(User user, String title, String description, boolean visibility, boolean collaborative) {
        final MediaList mediaList = new MediaList(user, title, description, visibility, collaborative);
        em.persist(mediaList);
        return mediaList;
    }

    @Override
    public void addToMediaList(MediaList mediaList, Media media) {
        if (mediaAlreadyInList(mediaList, media)) {
            return;
        }
        em.createNativeQuery("INSERT INTO listelement (mediaid, medialistid) VALUES (:mediaId, :mediaListId)")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("mediaListId", mediaList.getMediaListId())
                .executeUpdate();
    }

    public boolean mediaAlreadyInList(MediaList mediaList, Media media) {
        return ((Number) em.createNativeQuery("SELECT COUNT(*) FROM listelement WHERE medialistid = :mediaListId AND mediaid = :mediaId")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("mediaId", media.getMediaId())
                .getSingleResult()).intValue() != 0;
    }

    @Override
    public void addToMediaList(MediaList mediaList, List<Media> media) {
        media.forEach(m -> addToMediaList(mediaList, m));
    }

    @Override
    public void deleteMediaFromList(MediaList mediaList, Media media) {
        em.createNativeQuery("DELETE FROM listelement WHERE medialistid = :mediaListId AND mediaid = :mediaId")
                .setParameter("mediaId", media.getMediaId())
                .setParameter("mediaListId", mediaList.getMediaListId())
                .executeUpdate();
    }

    @Override
    public void deleteMediaFromList(MediaList mediaList, List<Media> media) {
        media.forEach(m -> deleteMediaFromList(mediaList, m));
    }

    @Override
    public void deleteList(MediaList mediaList) {
        em.remove(mediaList);
    }

    @Override
    public MediaList createMediaListCopy(User user, MediaList toCopy) {
        MediaList fork = new MediaList(user, "Copy from " + toCopy.getListName(), toCopy.getDescription(), toCopy.getVisible(), toCopy.getCollaborative());
        em.persist(fork);

        @SuppressWarnings("unchecked")
        List<Long> toCopyMediaIds = em.createNativeQuery("SELECT mediaid FROM listelement WHERE medialistid = :toCopyId")
                .setParameter("toCopyId", toCopy)
                .getResultList();
        List<Media> toCopyMedia = getMedias(toCopyMediaIds);
        addToMediaList(fork, toCopyMedia);
        addToForkedLists(toCopy, fork);
        return fork;
    }

    private void addToForkedLists(MediaList originalList, MediaList forkedList) {
        em.createNativeQuery("INSERT INTO forkedlists(originalistid, forkedlistid) VALUES (:originalListId, :forkedListId)")
                .setParameter("originalListId", originalList.getMediaListId())
                .setParameter("forkedListId", forkedList.getMediaListId())
                .executeUpdate();
    }

    @Override
    public boolean canEditList(User user, MediaList mediaList) {
        return !(((Number) em.createNativeQuery("SELECT COUNT(*) FROM medialist ml LEFT JOIN collaborative c on ml.medialistid = c.listid WHERE medialistid = :medialistid AND ((userid = :userid) OR " +
                        "(collaboratorid = :userid AND accepted = :accepted))")
                .setParameter("userid", user.getUserId())
                .setParameter("accepted", true)
                .setParameter("medialistid", mediaList.getMediaListId())
                .getSingleResult())
                .intValue() == 0);
    }

    @Override
    public PageContainer<MediaList> getUserEditableLists(User user, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = em.createNativeQuery("(SELECT medialistid FROM medialist WHERE userid = :userId) UNION " +
                        "(SELECT m.medialistid FROM collaborative c JOIN medialist m on c.listid = m.medialistid WHERE collaboratorid = :userId AND accepted = :accepted) " +
                        "OFFSET :offset LIMIT :limit")
                .setParameter("userId", user.getUserId())
                .setParameter("accepted", true)
                .setParameter("offset", (page - 1) * pageSize)
                .setParameter("limit", pageSize)
                .getResultList();

        long count = ((Number) em.createNativeQuery("SELECT COUNT(*) FROM ((SELECT * FROM medialist WHERE userid = :userId) UNION " +
                        "(SELECT m.* FROM collaborative c JOIN medialist m on c.listid = m.medialistid WHERE collaboratorid = :userId AND accepted = :accepted)) AS aux")
                .setParameter("userId", user.getUserId())
                .setParameter("accepted", true)
                .getSingleResult()).longValue();

        return new PageContainer<>(getMediaLists(listIds), page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> getListForks(MediaList mediaList, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        @SuppressWarnings("unchecked")
        List<Long> listIds = em.createNativeQuery("SELECT m.medialistid FROM forkedlists f JOIN medialist m ON f.forkedlistid = m.medialistid " +
                        "WHERE f.originalistid = :mediaListId AND m.visibility = :visibility " +
                        "OFFSET :offset LIMIT :limit")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("visibility", true)
                .setParameter("offset", (page - 1) * pageSize)
                .setParameter("limit", pageSize)
                .getResultList();

        long count = ((Number) em.createNativeQuery("SELECT COUNT(m.medialistid) FROM forkedlists f JOIN medialist m ON f.forkedlistid = m.medialistid " +
                        "WHERE f.originalistid = :mediaListId AND m.visibility = :visibility")
                .setParameter("mediaListId", mediaList.getMediaListId())
                .setParameter("visibility", true)
                .getSingleResult()).longValue();

        return new PageContainer<>(getMediaLists(listIds), page, pageSize, count);
    }

}
