package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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

    private final MessageSource messageSource;

    @Autowired
    public CollaborativeListsServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Request makeNewRequest(int listId, int userId) {
        try {
            User user = userDao.getById(userId).orElseThrow(RuntimeException::new);
            MediaList list = listsDao.getMediaListById(listId).orElseThrow(RuntimeException::new);
            User to = userDao.getById(list.getUserId()).orElseThrow(RuntimeException::new);
            final Map<String, Object> mailMap = new HashMap<>();
            mailMap.put("listname", list.getListName());
            mailMap.put("username", user.getUsername());
            final String subject = messageSource.getMessage("collabEmail.subject", null, Locale.getDefault());
            emailService.sendEmail(to.getEmail(), subject, "collaborationRequest.html", mailMap);
        } catch (RuntimeException e) {
            //TODO log correspondiente
        }
        return collaborativeListsDao.makeNewRequest(listId, userId);
    }

    @Override
    public PageContainer<Request> getRequestsByUserId(int userId, int page, int pageSize) {
        return collaborativeListsDao.getRequestsByUserId(userId, page, pageSize);
    }

    @Override
    public void acceptRequest(int collabId) {
        try {
            Request collaboration = collaborativeListsDao.getById(collabId).orElseThrow(RuntimeException::new);
            final Map<String, Object> mailMap = new HashMap<>();
            mailMap.put("listname", collaboration.getListname());
            mailMap.put("collabUsername", collaboration.getCollaboratorUsername());
            mailMap.put("listId", collaboration.getListId());
            final String subject = messageSource.getMessage("collabConfirmEmail.subject", null, Locale.getDefault());
            User to = userDao.getById(collaboration.getCollaboratorId()).orElseThrow(RuntimeException::new);
            emailService.sendEmail(to.getEmail(), subject, "collaborationConfirmed.html", mailMap);
        }catch (RuntimeException e) {
            //TODO log correspondiente
        }
        collaborativeListsDao.acceptRequest(collabId);
    }

    @Override
    public void rejectRequest(int collabId) {
        collaborativeListsDao.rejectRequest(collabId);
    }

    @Override
    public void deleteCollaborator(int collabId) {
        collaborativeListsDao.rejectRequest(collabId);
    }

    @Override
    public PageContainer<Request> getListCollaborators(int listId, int page, int pageSize) {
        return collaborativeListsDao.getListCollaborators(listId, page, pageSize);
    }

    @Override
    public Optional<Request> getById(int collabId) {
        return collaborativeListsDao.getById(collabId);
    }
}
