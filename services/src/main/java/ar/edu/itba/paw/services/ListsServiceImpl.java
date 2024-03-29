package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ListsDao;
import ar.edu.itba.paw.interfaces.ListsService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public PageContainer<Media> getMediaInList(MediaList mediaList, int page, int pageSize) {
        return listsDao.getMediaInList(mediaList, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getMediaListByFilters(int page, int pageSize, SortType sort, List<Genre> genre, int minMatches, LocalDateTime fromDate, LocalDateTime toDate, String term) {
        return listsDao.getMediaListByFilters(page, pageSize, sort, genre, minMatches, fromDate, toDate, term);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getListsIncludingMedia(Media media, int page, int pageSize) {
        return listsDao.getListsIncludingMedia(media, page, pageSize);
    }

    @Transactional
    @Override
    public MediaList createMediaList(User user, String title, String description, boolean visibility, boolean collaborative) {
        return listsDao.createMediaList(user, title, description, visibility, collaborative);
    }

    @Transactional
    @Override
    public void manageMedia(MediaList mediaList, List<Media> mediaToAdd, List<Media> mediaToRemove) {
        addToMediaList(mediaList, mediaToAdd);
        deleteMediaFromList(mediaList, mediaToRemove);
    }

    @Transactional
    @Override
    public void addToMediaList(MediaList mediaList, Media media) {
        listsDao.addToMediaList(mediaList, media);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean mediaAlreadyInList(MediaList mediaList, Media media) {
        return listsDao.mediaAlreadyInList(mediaList, media);
    }

    @Transactional
    @Override
    public void addToMediaList(MediaList mediaList, List<Media> media) {
        listsDao.addToMediaList(mediaList, media);
    }

    @Transactional
    @Override
    public void deleteMediaFromList(MediaList mediaList, Media media) {
        listsDao.deleteMediaFromList(mediaList, media);
    }

    @Transactional
    @Override
    public void deleteMediaFromList(MediaList mediaList, List<Media> media) {
        listsDao.deleteMediaFromList(mediaList, media);
    }

    @Transactional
    @Override
    public void deleteList(MediaList mediaList) {
        listsDao.deleteList(mediaList);
    }

    @Transactional
    @Override
    public MediaList updateList(MediaList mediaList, String title, String description, boolean visibility, boolean collaborative) {
        mediaList.setListName(title);
        mediaList.setDescription(description);
        mediaList.setVisible(visibility);
        mediaList.setCollaborative(collaborative);
        return mediaList;
    }

    @Transactional
    @Override
    public MediaList createMediaListCopy(User user, MediaList toCopy) {
        return listsDao.createMediaListCopy(user, toCopy);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean canEditList(User user, MediaList mediaList) {
        return listsDao.canEditList(user, mediaList);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getUserEditableLists(User user, int page, int pageSize) {
        return listsDao.getUserEditableLists(user, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getListForks(MediaList mediaList, int page, int pageSize) {
        return listsDao.getListForks(mediaList, page, pageSize);
    }

}
