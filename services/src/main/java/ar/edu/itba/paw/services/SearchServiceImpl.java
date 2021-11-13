package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CollaborativeListsDao;
import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;
    @Autowired
    private CollaborativeListsDao collaborativeListsDao;

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> searchMediaByTitleNotInList(MediaList mediaList, String title, int page, int pageSize, List<MediaType> mediaType, SortType sort) {
        return searchDao.searchMediaByTitleNotInList(mediaList, title, page, pageSize, mediaType, sort);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<User> searchUsersToCollabNotInList(String username, MediaList mediaList, int pageSize, int page) {
        List<User> collaborators = new ArrayList<>();
        collaborators.add(mediaList.getUser());
        List<Request> requestList = collaborativeListsDao.getListCollaborators(mediaList, 0, 50).getElements();
        if (!requestList.isEmpty()) requestList.forEach((request -> collaborators.add(request.getCollaborator())));
        return searchDao.searchUserByUsername(username, collaborators, page, pageSize);
    }

}
