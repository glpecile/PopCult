package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudioDao;
import ar.edu.itba.paw.interfaces.StudioService;
import ar.edu.itba.paw.models.staff.Studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {
    @Autowired
    private StudioDao studioDao;

    @Override
    public List<Studio> getStudioByMediaId(int mediaId) {
        return studioDao.getStudioByMediaId(mediaId);
    }

    @Override
    public List<Studio> getStudios() {
        return studioDao.getStudios();
    }
}
