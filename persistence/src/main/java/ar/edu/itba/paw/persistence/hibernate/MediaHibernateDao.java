package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return null;
    }

    @Override
    public PageContainer<Media> getMediaList(int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<Media> getMediaList(int mediaType, int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<Media> getLatestMediaList(int mediaType, int page, int pageSize) {
        return null;
    }
}
