package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.interfaces.StudioService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.staff.Studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudioServiceImpl implements StudioService {
    @Autowired
    private StudioDao studioDao;

    @Override
    public Optional<Studio> getById(int studioId) {
        return studioDao.getById(studioId);
    }

    @Override
    public List<Studio> getStudioByMediaId(int mediaId) {
        return studioDao.getStudioByMediaId(mediaId);
    }

    @Override
    public PageContainer<Integer> getMediaByStudio(int studioId, int page, int pageSize) {
        return studioDao.getMediaByStudioIds(studioId, page, pageSize);
    }

    @Override
    public Optional<Integer> getMediaCountByStudio(int studioId) {
        return studioDao.getMediaCountByStudio(studioId);
    }

    @Override
    public List<Studio> getStudios() {
        return studioDao.getStudios();
    }
}
