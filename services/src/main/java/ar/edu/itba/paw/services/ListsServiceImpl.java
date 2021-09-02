package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.models.lists.MediaList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ListsServiceImpl implements ListsService {
    @Autowired
    private ListsDao listsDao;

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return listsDao.getMediaListById(mediaListId);
    }
}
