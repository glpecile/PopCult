package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.ModeratorService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    @Autowired
    private ModeratorDao moderatorDao;


    @Override
    public PageContainer<User> getModerators(int page, int pageSize) {
        return moderatorDao.getModerators(page, pageSize);
    }

    @Override
    public void promoteToMod(int userId) {
        moderatorDao.promoteToMod(userId);
        removeRequest(userId);
    }

    @Override
    public void removeMod(int userId) {
        moderatorDao.removeMod(userId);
    }

    @Override
    public PageContainer<User> getModRequesters(int page, int pageSize) {
        return moderatorDao.getModRequesters(page, pageSize);
    }

    @Override
    public void addModRequest(int userId) {
        moderatorDao.addModRequest(userId);
    }

    @Override
    public void removeRequest(int userId) {
        moderatorDao.removeRequest(userId);
    }
}
