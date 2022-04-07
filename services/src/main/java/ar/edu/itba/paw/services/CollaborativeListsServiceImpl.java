package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CollaborativeListService;
import ar.edu.itba.paw.interfaces.CollaborativeListsDao;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.exceptions.CollaboratorRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyCollaboratesInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
    public Request makeNewRequest(MediaList mediaList, User user) throws CollaboratorRequestAlreadyExistsException {
        Request request = collaborativeListsDao.makeNewRequest(mediaList, user);
        emailService.sendNewRequestEmail(mediaList, user, mediaList.getUser(), LocaleContextHolder.getLocale());
        return request;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Request> getById(int collabId) {
        return collaborativeListsDao.getById(collabId);
    }

    @Override
    public Optional<Request> getUserListCollabRequest(MediaList mediaList, User user) {
        return collaborativeListsDao.getUserListCollabRequest(mediaList, user);
    }


    @Transactional(readOnly = true)
    @Override
    public PageContainer<Request> getListCollaborators(MediaList mediaList, int page, int pageSize) {
        return collaborativeListsDao.getListCollaborators(mediaList, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Request> getRequestsByUser(User user, int page, int pageSize) {
        return collaborativeListsDao.getRequestsByUser(user, page, pageSize);
    }

    @Transactional
    @Override
    public void acceptRequest(Request collaborationRequest) {
        emailService.sendCollabRequestAcceptedEmail(collaborationRequest.getCollaborator(), collaborationRequest, LocaleContextHolder.getLocale());
        collaborationRequest.setAccepted(true);
    }

    @Transactional
    @Override
    public void rejectRequest(Request request) {
        collaborativeListsDao.rejectRequest(request);
    }

    @Transactional
    @Override
    public void addCollaborator(MediaList mediaList, User user) {
        collaborativeListsDao.addCollaborator(mediaList, user);
    }

    @Transactional
    @Override
    public void deleteCollaborator(Request request) {
        collaborativeListsDao.rejectRequest(request);
    }

    @Transactional
    @Override
    public void addCollaborators(MediaList mediaList, List<User> users){
        collaborativeListsDao.addCollaborators(mediaList, users);
    }
}
