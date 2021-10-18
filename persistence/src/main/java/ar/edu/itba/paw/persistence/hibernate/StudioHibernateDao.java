package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;

import java.util.List;
import java.util.Optional;

public class StudioHibernateDao implements StudioDao {

    @Override
    public Optional<Studio> getById(int studioId) {
        return Optional.empty();
    }

    @Override
    public List<Studio> getStudioByMediaId(int mediaId) {
        return null;
    }

    @Override
    public PageContainer<Media> getMediaByStudio(int studioId, int page, int pageSize) {
        return null;
    }

    @Override
    public Optional<Integer> getMediaCountByStudio(int studioId) {
        return Optional.empty();
    }

    @Override
    public List<Studio> getStudios() {
        return null;
    }
}
