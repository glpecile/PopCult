package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.interfaces.StudioService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudioServiceImpl implements StudioService {
    @Autowired
    private StudioDao studioDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Studio> getById(int studioId) {
        return studioDao.getById(studioId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Studio> getStudioByMediaId(int mediaId) {
        return studioDao.getStudioByMediaId(mediaId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByStudio(Studio studio, int page, int pageSize) {
        return studioDao.getMediaByStudio(studio, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Integer> getMediaCountByStudio(int studioId) {
        return studioDao.getMediaCountByStudio(studioId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Studio> getStudios() {
        return studioDao.getStudios();
    }
}
