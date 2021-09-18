package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.models.PageContainer;
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
    public List<MediaList> getMediaListById(List<Integer> mediaListId) {
        return listsDao.getMediaListById(mediaListId);
    }

    @Override
    public PageContainer<MediaList> getAllLists(int page, int pageSize) {
        return listsDao.getAllLists(page, pageSize);
    }

    @Override
    public List<MediaList> getMediaListByUserId(int userId) {
        return listsDao.getMediaListByUserId(userId);
    }

    @Override
    public PageContainer<MediaList> getMediaListByUserId(int userId, int page, int pageSize) {
        return listsDao.getMediaListByUserId(userId, page, pageSize);
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
    public PageContainer<Integer> getMediaIdInList(int mediaListId, int page, int pageSize){
        return listsDao.getMediaIdInListIds(mediaListId, page, pageSize);
    }

    @Override
    public PageContainer<MediaList> getLastAddedLists(int page, int pageSize) {
        return listsDao.getLastAddedLists(page, pageSize);
    }

    @Override
    public List<MediaList> getNLastAddedList(int amount) {
        return listsDao.getNLastAddedList(amount);
    }

    @Override
    public PageContainer<MediaList> getListsIncludingMediaId(int mediaId, int page, int pageSize) {
        return listsDao.getListsIncludingMediaId(mediaId, page, pageSize);
    }

    @Override
    public Optional<Integer> getListCount() {
        return listsDao.getListCount();
    }

    @Override
    public Optional<Integer> getListCountFromUserId(int userId) {
        return listsDao.getListCountFromUserId(userId);
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
    public MediaList createMediaList(int userId, String title, String description, boolean visibility, boolean collaborative) {
        return listsDao.createMediaList(userId, title, description, visibility, collaborative);
    }

    @Override
    public void addToMediaList(int mediaListId, int mediaId) {
        listsDao.addToMediaList(mediaListId, mediaId);
    }

    @Override
    public void addToMediaList(int mediaListId, List<Integer> mediaIdList) {
        listsDao.addToMediaList(mediaListId, mediaIdList);
    }

    @Override
    public void deleteMediaFromList(int mediaListId, int mediaId) {
        listsDao.deleteMediaFromList(mediaListId, mediaId);
    }

    @Override
    public void deleteList(int mediaListId) {
        listsDao.deleteList(mediaListId);
    }

    @Override
    public void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative) {
        listsDao.updateList(mediaListId, title, description, visibility, collaborative);
    }

    @Override
    public MediaList createMediaListCopy(int userId, int toCopy) {
        return listsDao.createMediaListCopy(userId, toCopy);
    }
}
