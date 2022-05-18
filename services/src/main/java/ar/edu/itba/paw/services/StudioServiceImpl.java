package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.interfaces.StudioService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PageContainer<Media> getMediaByStudio(Studio studio, int page, int pageSize) {
        return studioDao.getMediaByStudio(studio, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Studio> getAllStudios(int page, int pageSize) {
        return studioDao.getAllStudios(page, pageSize);
    }

}
