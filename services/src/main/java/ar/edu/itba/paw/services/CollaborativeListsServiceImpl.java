package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CollaborativeListService;
import ar.edu.itba.paw.interfaces.CollaborativeListsDao;
import ar.edu.itba.paw.models.collaborative.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaborativeListsServiceImpl implements CollaborativeListService {
    @Autowired
    private CollaborativeListsDao collaborativeListsDao;

    @Override
    public Request makeNewRequest(int listId, int userId, String message, int collabType) {
        return collaborativeListsDao.makeNewRequest(listId, userId, message, collabType);
    }

    @Override
    public List<Request> getRequestsByUserId(int userId) {
        return collaborativeListsDao.getRequestsByUserId(userId);
    }
}
