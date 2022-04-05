package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyCollaboratesInListException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface CollaborativeListsDao {
    Request makeNewRequest(MediaList mediaList, User user);

    Optional<Request> getById(int collabId);

    Optional<Request> getUserListCollabRequest(MediaList mediaList, User user);

    PageContainer<Request> getListCollaborators(MediaList mediaList, int page, int pageSize);

    PageContainer<Request> getRequestsByUser(User user, int page, int pageSize);

    void rejectRequest(Request request);

    void addCollaborator(MediaList mediaList, User user);

    void addCollaborators(MediaList mediaList, List<User> users);

}
