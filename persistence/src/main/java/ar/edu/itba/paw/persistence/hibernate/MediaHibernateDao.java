package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public PageContainer<Media> getMediaList(int page, int pageSize) {
        return null;
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
}
