package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;

import java.util.List;
import java.util.Optional;

public interface CollaborativeListService {
    Request makeNewRequest(int listId, int userId);

    PageContainer<Request> getRequestsByUserId(int userId, int page, int pageSize);

    void acceptRequest(int collabId);

    void rejectRequest(int collabId);

    void deleteCollaborator(int collabId);

    PageContainer<Request> getListCollaborators(int listId, int page, int pageSize);

    Optional<Request> getById(int collabId);
}
