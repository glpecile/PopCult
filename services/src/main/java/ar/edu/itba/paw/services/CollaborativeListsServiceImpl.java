package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CollaborativeListsServiceImpl implements CollaborativeListService {
    @Autowired
    private CollaborativeListsDao collaborativeListsDao;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ListsDao listsDao;

    @Transactional
    @Override
    public Request makeNewRequest(int listId, int userId) {
        userDao.getById(userId).ifPresent(user -> listsDao.getMediaListById(listId).ifPresent(list -> userDao.getById(list.getUserId()).ifPresent(listOwner -> emailService.sendNewRequestEmail(list, user, listOwner))));
        return collaborativeListsDao.makeNewRequest(listId, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Request> getRequestsByUserId(int userId, int page, int pageSize) {
        return collaborativeListsDao.getRequestsByUserId(userId, page, pageSize);
    }

    @Transactional
    @Override
    public void acceptRequest(int collabId) {
        collaborativeListsDao.getById(collabId).ifPresent((collaboration -> userDao.getById(collaboration.getCollaboratorId()).ifPresent(user -> emailService.sendCollabRequestAcceptedEmail(user, collaboration))));
        collaborativeListsDao.acceptRequest(collabId);
    }

    @Transactional
    @Override
    public void rejectRequest(int collabId) {
        collaborativeListsDao.rejectRequest(collabId);
    }

    @Transactional
    @Override
    public void deleteCollaborator(int collabId) {
        collaborativeListsDao.rejectRequest(collabId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Request> getListCollaborators(int listId, int page, int pageSize) {
        return collaborativeListsDao.getListCollaborators(listId, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Request> getById(int collabId) {
        return collaborativeListsDao.getById(collabId);
    }
}
