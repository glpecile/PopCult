package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
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
public class MediaHibernateDao implements MediaDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Media> getById(int mediaId) {
        return Optional.ofNullable(em.find(Media.class, mediaId));
    }

    @Override
    public List<Media> getById(List<Integer> mediaIds) {
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in :mediaIds", Media.class);
        query.setParameter("mediaIds", mediaIds);
        return mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();
    }

    @Override
    public PageContainer<Media> getMediaList(MediaType mediaType, int page, int pageSize) {
        //Para paginacion
        //Pedimos el contenido paginado.
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM media WHERE type = :type OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("type", mediaType.ordinal());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createQuery("SELECT COUNT(*) AS count FROM Media where type = :type");
        countQuery.setParameter("type", mediaType);
        final long count = (long) countQuery.getSingleResult();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaids)", Media.class);
        query.setParameter("mediaids", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    @Override
    public PageContainer<Media> getLatestMediaList(MediaType mediaType, int page, int pageSize) {
        //Para paginacion
        //Pedimos el contenido paginado.
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM media WHERE type = :type ORDER BY releasedate DESC OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("type", mediaType.ordinal());
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createQuery("SELECT COUNT(mediaId) FROM Media WHERE type = :type");
        countQuery.setParameter("type", mediaType);
        final long count = (long) countQuery.getSingleResult();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaids)", Media.class);
        query.setParameter("mediaids", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    private Query buildAndWhereStatement(String baseQuery, Integer page, Integer pageSize, List<MediaType> mediaType, SortType sort, List<Genre> genre, Date fromDate, Date toDate){
        StringBuilder toReturn = new StringBuilder();
        final Map<String, Object> parameters = new HashMap<>();
        toReturn.append(baseQuery);
        LinkedList<String> wheres = new LinkedList<>();

        if(!genre.isEmpty()){
            wheres.add(" genreid IN ( :genres)");
            parameters.put("genres", genre.stream().map(Genre::ordinal).collect(Collectors.toList()));
        }
        if(!mediaType.isEmpty()){
            wheres.add(" type IN ( :types)");
            parameters.put("types", mediaType.stream().map(MediaType::ordinal).collect(Collectors.toList()));

        }
        if(fromDate != null && toDate != null) {
            wheres.add(" releasedate BETWEEN :fromDate AND :toDate ");
            parameters.put("fromDate", fromDate);
            parameters.put("toDate", toDate);
        }

        if(!wheres.isEmpty()){
            toReturn.append("WHERE ");
            toReturn.append(wheres.removeFirst());
            wheres.forEach(where -> toReturn.append(" AND ").append(where));
        }
        if(sort != null)
            toReturn.append(" ORDER BY ").append(sort.nameMedia);

        if(page != null && pageSize != null){
            toReturn.append( " OFFSET :offset LIMIT :limit ");
            parameters.put("offset", page*pageSize);
            parameters.put("limit", pageSize);
        }
        toReturn.append(" ) AS aux");

        final Query nativeQuery = em.createNativeQuery(toReturn.toString());
        parameters.forEach(nativeQuery::setParameter);
        return nativeQuery;
    }
    @Override
    public PageContainer<Media> getMediaByFilters(List<MediaType> mediaType, int page, int pageSize, SortType sort, List<Genre> genre, Date fromDate, Date toDate) {
        //Para paginacion
        //Pedimos el contenido paginado.
        final String baseQuery = "SELECT mediaid FROM ( SELECT DISTINCT mediaid, " + sort.nameMedia +" FROM  media NATURAL JOIN mediagenre ";
        final Query nativeQuery = buildAndWhereStatement(baseQuery,page,pageSize,mediaType,sort, genre,fromDate,toDate);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();

        //Obtenemos la cantidad total de elementos.
        final String countBaseQuery = "SELECT COUNT(mediaid) FROM( SELECT DISTINCT mediaid FROM media NATURAL JOIN mediagenre ";
        final Query countQuery = buildAndWhereStatement(countBaseQuery,null,null,mediaType,null,genre,fromDate,toDate);
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaids)", Media.class);
        query.setParameter("mediaids", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }
}
