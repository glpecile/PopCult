package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;

import java.util.List;

public interface CollaborativeListService {
    Request makeNewRequest(int listId, int userId);

    PageContainer<Request> getRequestsByUserId(int userId, int page, int pageSize);
}
