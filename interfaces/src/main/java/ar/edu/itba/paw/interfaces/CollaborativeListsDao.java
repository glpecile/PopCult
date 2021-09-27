package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.collaborative.Request;

import java.util.List;

public interface CollaborativeListsDao {
    Request makeNewRequest(int listId, int userId,String message, int collabType);

    List<Request> getRequestsByUserId(int userId);
}
