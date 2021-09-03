package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ListsServiceImpl implements ListsService {
    @Autowired
    private ListsDao listsDao;

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return listsDao.getMediaListById(mediaListId);
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId) {
        return listsDao.getMediaListByUserId(userId);
    }

    @Override
    public List<MediaList> getDiscoveryMediaLists() {
        return listsDao.getDiscoveryMediaLists();
    }

    @Override
    public List<Integer> getMediaIdInList(int mediaListId) {
        return listsDao.getMediaIdInList(mediaListId);
    }
}
