package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.interfaces.exceptions.MediaAlreadyInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ListsServiceImpl implements ListsService {
    @Autowired
    private ListsDao listsDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<MediaList> getMediaListById(int mediaListId) {
        return listsDao.getMediaListById(mediaListId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getAllLists(int page, int pageSize) {
        return listsDao.getAllLists(page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getMediaListByUser(User user, int page, int pageSize) {
        return listsDao.getMediaListByUser(user, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getPublicMediaListByUser(User user, int page, int pageSize) {
        return listsDao.getPublicMediaListByUser(user, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Media> getMediaIdInList(MediaList mediaList) {
        return listsDao.getMediaIdInList(mediaList);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaIdInList(MediaList mediaList, int page, int pageSize) {
        return listsDao.getMediaIdInList(mediaList, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getLastAddedLists(int page, int pageSize) {
        return listsDao.getLastAddedLists(page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize) {
        return listsDao.getListsIncludingMedia(media, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MediaList> getListsContainingGenre(Genre genre, int pageSize, int minMatches) {
        return listsDao.getListsContainingGenre(genre, pageSize, minMatches);
    }

    @Transactional
    @Override
    public MediaList createMediaList(User user, String title, String description, boolean visibility, boolean collaborative) {
        return listsDao.createMediaList(user, title, description, visibility, collaborative);
    }

    @Transactional
    @Override
    public void addToMediaList(int mediaListId, int mediaId) throws MediaAlreadyInListException {
        listsDao.addToMediaList(mediaListId, mediaId);
    }

    @Transactional
    @Override
    public void addToMediaList(int mediaListId, List<Integer> mediaIdList) throws MediaAlreadyInListException {
        listsDao.addToMediaList(mediaListId, mediaIdList);
    }

    @Transactional
    @Override
    public void deleteMediaFromList(int mediaListId, int mediaId) {
        listsDao.deleteMediaFromList(mediaListId, mediaId);
    }

    @Transactional
    @Override
    public void deleteList(int mediaListId) {
        listsDao.deleteList(mediaListId);
    }

    @Transactional
    @Override
    public void updateList(int mediaListId, String title, String description, boolean visibility, boolean collaborative) {
        listsDao.updateList(mediaListId, title, description, visibility, collaborative);
    }

    @Transactional
    @Override
    public MediaList createMediaListCopy(int userId, int toCopy) {
        return listsDao.createMediaListCopy(userId, toCopy);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getListOwner(int listId) {
        return listsDao.getListOwner(listId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean canEditList(int userId, int listId) {
        return listsDao.canEditList(userId, listId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getUserEditableLists(int userId, int page, int pageSize) {
        return listsDao.getUserEditableLists(userId, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getListForks(int listId, int page, int pageSize) {
        return listsDao.getListForks(listId, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MediaList> getForkedFrom(int listId) {
        return listsDao.getForkedFrom(listId);
    }
}
