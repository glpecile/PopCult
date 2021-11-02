package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Primary
@Repository
public class SearchHibernateDao implements SearchDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public PageContainer<Media> searchMediaByTitleNotInList(MediaList mediaList, String title, int page, int pageSize, List<MediaType> mediaType, SortType sort) {
        //Para paginacion
        //Pedimos el contenido paginado.
        String orderBy = " ORDER BY " + sort.nameMedia;
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM media WHERE title ILIKE CONCAT('%', :title, '%') AND type IN (:mediaType) AND mediaid NOT IN (SELECT mediaid FROM listelement WHERE medialistid = :mediaListId)" + orderBy + " OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("title", title);
        nativeQuery.setParameter("mediaListId", mediaList.getMediaListId());
        nativeQuery.setParameter("mediaType", mediaType.stream().map(MediaType::ordinal).collect(Collectors.toList()));
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) FROM media WHERE title ILIKE CONCAT('%', :title, '%') AND type IN (:mediaType)  AND mediaid NOT IN (SELECT mediaid FROM listelement WHERE medialistid = :mediaListId)");
        countQuery.setParameter("title", title);
        countQuery.setParameter("mediaListId", mediaList.getMediaListId());
        countQuery.setParameter("mediaType", mediaType.stream().map(MediaType::ordinal).collect(Collectors.toList()));
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaIds)", Media.class);
        query.setParameter("mediaIds", mediaIds);
        List<Media> mediasList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediasList, page, pageSize, count);
    }

    private Query buildAndWhereStatement(String baseQuery, String title, int page, int pageSize, List<MediaType> mediaType, SortType sort, List<Genre> genre, Date fromDate, Date toDate){
        StringBuilder toReturn = new StringBuilder();
        final Map<String, Object> parameters = new HashMap<>();
        toReturn.append(baseQuery);

        toReturn.append(" WHERE title ILIKE CONCAT('%', :title, '%') ");
        parameters.put("title", title);

        if(!genre.isEmpty()){
            toReturn.append(" AND genreid IN ( :genres)");
            parameters.put("genres", genre.stream().map(Genre::ordinal).collect(Collectors.toList()));
        }
        if(!mediaType.isEmpty()){
            toReturn.append(" AND type IN ( :types)");
            parameters.put("types", mediaType.stream().map(MediaType::ordinal).collect(Collectors.toList()));

        }
        if(fromDate != null && toDate != null) {
            toReturn.append(" AND releasedate BETWEEN :fromDate AND :toDate ");
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
        }

        toReturn.append(" ORDER BY ").append(sort.nameMedia);

        toReturn.append(" OFFSET :offset LIMIT :limit ");
        parameters.put("offset", page*pageSize);
        parameters.put("limit", pageSize);

        toReturn.append(" ) AS aux");

        final Query nativeQuery = em.createNativeQuery(toReturn.toString());
        parameters.forEach(nativeQuery::setParameter);
        return nativeQuery;
    }
    //unicamente para listas
    private Query buildAndWhereStatement(String baseQuery, String name, int page, int pageSize, boolean visibility,SortType sort, List<Genre> genre, int minMatches){
        StringBuilder toReturn = new StringBuilder();
        final Map<String, Object> parameters = new HashMap<>();
        toReturn.append(baseQuery);

        toReturn.append(" WHERE listname ILIKE CONCAT('%', :listname, '%') ");
        parameters.put("listname", name);

        if(!genre.isEmpty()){
            toReturn.append(" AND genreid IN ( :genres) ");
            parameters.put("genres", genre.stream().map(Genre::ordinal).collect(Collectors.toList()));
            toReturn.append(" GROUP BY medialistid, visibility,").append(sort.nameMediaList).append(" HAVING COUNT(mediaId) >= :minMatches ");
            parameters.put("minMatches", minMatches);
        }
        toReturn.append(" AND visibility = :visibility");
        parameters.put("visibility", visibility);

        toReturn.append(" ORDER BY ").append(sort.nameMediaList);
//        parameters.put("sort", sort.colNumberMediaList);

        toReturn.append( " OFFSET :offset LIMIT :limit ");
        parameters.put("offset", page*pageSize);
        parameters.put("limit", pageSize);

        toReturn.append(" ) AS aux");
        final Query nativeQuery = em.createNativeQuery(toReturn.toString());
        parameters.forEach(nativeQuery::setParameter);
        return nativeQuery;
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, List<MediaType> mediaType, SortType sort, List<Genre> genre, Date fromDate, Date toDate) {
        //Para paginacion
        //Pedimos el contenido paginado.
        final String baseQuery = "SELECT mediaid FROM ( SELECT DISTINCT mediaid, " + sort.nameMedia +" FROM  media NATURAL JOIN mediagenre ";
        final Query nativeQuery = buildAndWhereStatement(baseQuery,title,page,pageSize,mediaType,sort, genre,fromDate,toDate);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();

        //Obtenemos la cantidad total de elementos.
        final String countBaseQuery = "SELECT COUNT(mediaid) FROM( SELECT DISTINCT mediaid, " + sort.nameMedia +" FROM media NATURAL JOIN mediagenre ";
        final Query countQuery = buildAndWhereStatement(countBaseQuery,title,page,pageSize,mediaType,sort,genre,fromDate,toDate);
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaIds)", Media.class);
        query.setParameter("mediaIds", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    @Override
    public PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, SortType sort, List<Genre> genre, int minMatches) {
        //Para paginacion
        //Pedimos el contenido paginado.
        final String baseQuery = "SELECT medialistid FROM (SELECT DISTINCT medialistid, " + sort.nameMediaList + " FROM mediaGenre NATURAL JOIN listelement NATURAL JOIN mediaList ";
        final Query nativeQuery = buildAndWhereStatement(baseQuery,name,page,pageSize,true,sort, genre, minMatches);
        @SuppressWarnings("unchecked")
        List<Long> mediaListIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final String countBaseQuery = "SELECT COUNT(medialistid) FROM (SELECT DISTINCT medialistid, " + sort.nameMediaList + " FROM mediaGenre NATURAL JOIN listelement NATURAL JOIN mediaList ";
        final Query countQuery = buildAndWhereStatement(countBaseQuery,name,page,pageSize,true,sort,genre,minMatches);
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<MediaList> query = em.createQuery("from MediaList where mediaListId in (:mediaListIds)", MediaList.class);
        query.setParameter("mediaListIds", mediaListIds);
        List<MediaList> mediaList = mediaListIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    @Override
    public PageContainer<User> searchUserByUsername(String username, List<User> excludedUsers, int page, int pageSize) {
        StringBuilder nativeString = new StringBuilder("SELECT userid FROM users WHERE users.username ILIKE CONCAT('%', :username, '%')");
        List<Integer> excludedUserIds = new ArrayList<>();
        excludedUsers.forEach( user -> excludedUserIds.add(user.getUserId()));
        if (!excludedUserIds.isEmpty()) nativeString.append("AND users.userId NOT IN :excludedUserIds");
        nativeString.append("OFFSET :offset LIMIT :limit");
        final Query nativeQuery = em.createNativeQuery(nativeString.toString());
        nativeQuery.setParameter("username", username);
        nativeQuery.setParameter("offset", page*pageSize);
        nativeQuery.setParameter("limit", pageSize);
        if (!excludedUserIds.isEmpty()) nativeQuery.setParameter("excludedUserIds", excludedUserIds);
        @SuppressWarnings("unchecked")
        final List<Long> userIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(userid) FROM users WHERE users.username ILIKE CONCAT('%', :username, '%') AND users.userId NOT IN :excludedUserIds")
                .setParameter("username", username).setParameter("excludedUserIds", excludedUserIds);
        int count = countQuery.getFirstResult();

        final TypedQuery<User> query = em.createQuery("from User WHERE userId IN :userIds", User.class);
        query.setParameter("userIds", userIds);
        List<User> userLists = userIds.isEmpty() ? Collections.emptyList() : query.getResultList();
        return new PageContainer<>(userLists, page, pageSize, count);
    }
}
