package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.CollaboratorRequestAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.CollabRequest;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface CollaborativeListsDao {
    CollabRequest makeNewRequest(MediaList mediaList, User user) throws CollaboratorRequestAlreadyExistsException;

    Optional<CollabRequest> getById(int collabId);

    Optional<CollabRequest> getUserListCollabRequest(MediaList mediaList, User user);

    PageContainer<CollabRequest> getListCollaborators(MediaList mediaList, int page, int pageSize);

    PageContainer<CollabRequest> getRequestsByUser(User user, int page, int pageSize);

    void rejectRequest(CollabRequest request);

    void addCollaborator(MediaList mediaList, User user);

    void addCollaborators(MediaList mediaList, List<User> users);

    void deleteCollaborators(MediaList mediaList, List<User> users);
}
