package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface CollaborativeListService {
    Request makeNewRequest(MediaList mediaList, User user);

    PageContainer<Request> getRequestsByUserId(User user, int page, int pageSize);

    void acceptRequest(Request collaborationRequest);

    void rejectRequest(Request request);

    void deleteCollaborator(Request request);

    PageContainer<Request> getListCollaborators(MediaList mediaList, int page, int pageSize);

    Optional<Request> getById(int collabId);
}
