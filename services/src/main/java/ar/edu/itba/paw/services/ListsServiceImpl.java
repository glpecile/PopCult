package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.models.lists.MediaList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListsServiceImpl implements ListsService {
    @Autowired
    private ListsDao listsDao;

    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return listsDao.getMediaListById(mediaListId);
    }

    @Override
    public List<MediaList> getAllLists(int page, int pageSize) {
        return listsDao.getAllLists(page, pageSize);
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId) {
        return listsDao.getMediaListByUserId(userId);
    }

    @Override
    public List<MediaList> getDiscoveryMediaLists(int pageSize) {
        return listsDao.getDiscoveryMediaLists(pageSize);
    }

    @Override
    public List<Integer> getMediaIdInList(int mediaListId) {
        return listsDao.getMediaIdInList(mediaListId);
    }

    @Override
    public List<MediaList> getLastAddedLists(int page, int pageSize) {
        return listsDao.getLastAddedLists(page, pageSize);
    }

    @Override
    public List<MediaList> getNLastAddedList(int amount) {
        return listsDao.getNLastAddedList(amount);
    }

    @Override
    public List<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize) {
        return listsDao.getListsIncludingMediaId(mediaId, page, pageSize);
    }

    @Override
    public Optional<Integer> getListCount() {
        return listsDao.getListCount();
    }

    @Override
    public Optional<Integer> getListCountFromMedia(int mediaId) {
        return listsDao.getListCountFromMedia(mediaId);
    }

    @Override
    public List<MediaList> getListsContainingGenre(int genreId, int pageSize, int minMatches) {
        return listsDao.getListsContainingGenre(genreId, pageSize, minMatches);
    }

    @Override
    public MediaList createMediaList(String title, String description, String image, int visibility, int collaborative) {
        return listsDao.createMediaList(title,description,image,visibility,collaborative);
    }
}
