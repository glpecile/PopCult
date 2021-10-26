package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface ModeratorDao {
    PageContainer<User> getModerators(int page, int pageSize);

    PageContainer<User> getModRequesters(int page, int pageSize);

    ModRequest addModRequest(User user) throws ModRequestAlreadyExistsException;

    void removeRequest(User user);
}
