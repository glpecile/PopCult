package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        collaborativeListsDao.acceptRequest(collabId);
    }

    @Override
    public void rejectRequest(int collabId) {
        collaborativeListsDao.rejectRequest(collabId);
    }
}
