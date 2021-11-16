package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyCollaboratesInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CollaborativeListsServiceImpl implements CollaborativeListService {
    @Autowired
    private CollaborativeListsDao collaborativeListsDao;
    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public Request makeNewRequest(MediaList mediaList, User user) {
        Optional<Request> request = collaborativeListsDao.getUserListCollabRequest(mediaList, user);
        if (request.isPresent()) {
            return request.get();
        }
        emailService.sendNewRequestEmail(mediaList, user, mediaList.getUser());
        return collaborativeListsDao.makeNewRequest(mediaList, user);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Request> getRequestsByUserId(User user, int page, int pageSize) {
        return collaborativeListsDao.getRequestsByUserId(user, page, pageSize);
    }

    @Transactional
    @Override
    public void acceptRequest(Request collaborationRequest) {
        emailService.sendCollabRequestAcceptedEmail(collaborationRequest.getCollaborator(), collaborationRequest);
        collaborationRequest.setAccepted(true);
    }

    @Transactional
    @Override
    public void rejectRequest(Request request) {
        collaborativeListsDao.rejectRequest(request);
    }

    @Transactional
    @Override
    public void deleteCollaborator(Request request) {
        collaborativeListsDao.rejectRequest(request);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Request> getListCollaborators(MediaList mediaList, int page, int pageSize) {
        return collaborativeListsDao.getListCollaborators(mediaList, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Request> getById(int collabId) {
        return collaborativeListsDao.getById(collabId);
    }

    @Transactional
    @Override
    public void addCollaborators(MediaList mediaList, List<User> users) throws UserAlreadyCollaboratesInListException {
        collaborativeListsDao.addCollaborators(mediaList, users);
    }
}
