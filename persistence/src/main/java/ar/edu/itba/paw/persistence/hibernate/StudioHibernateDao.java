package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class StudioHibernateDao implements StudioDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudioHibernateDao.class);

    @Override
    public Optional<Studio> getById(int studioId) {

        return Optional.ofNullable(em.find(Studio.class, studioId));
    }

    @Override
    public PageContainer<Media> getMediaByStudio(Studio studio, int page, int pageSize) {
        //Para paginacion
        //Pedimos el contenido paginado.
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM mediaStudio NATURAL JOIN media WHERE studioId = :studioid OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("studioid",studio.getStudioId());
        nativeQuery.setParameter("offset",page*pageSize);
        nativeQuery.setParameter("limit",pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createNativeQuery("SELECT COUNT(*) AS count FROM mediaStudio where studioId = :studioid");
        countQuery.setParameter("studioid", studio.getStudioId());
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaids)", Media.class);
        query.setParameter("mediaids", mediaIds);
        List<Media> mediaList = query.getResultList();

        return new PageContainer<>(mediaList,page,pageSize,count);
    }

}
