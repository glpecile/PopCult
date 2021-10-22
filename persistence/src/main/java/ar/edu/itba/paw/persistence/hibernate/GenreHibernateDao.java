package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.GenreDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Primary
@Repository
public class GenreHibernateDao implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public PageContainer<Media> getMediaByGenre(Genre genre, int page, int pageSize) {
        //Para paginacion
        //Pedimos el contenido paginado.
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM mediagenre  WHERE genreid = :genreid OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("genreid",genre.ordinal());
        nativeQuery.setParameter("offset",page*pageSize);
        nativeQuery.setParameter("limit",pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createNativeQuery("SELECT COUNT(*) AS count FROM mediagenre where genreid = :genreid");
        countQuery.setParameter("genreid", genre.ordinal());
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaids)", Media.class);
        query.setParameter("mediaids", mediaIds);
        List<Media> mediaList = query.getResultList();

        return new PageContainer<>(mediaList,page,pageSize,count);
    }

    @Override
    public List<MediaList> getListsContainingGenre(Genre genre, int pageSize, int minMatches) {
        //todo lo moveria a genreDAO la hace rober despues.
        return null;
    }

}
