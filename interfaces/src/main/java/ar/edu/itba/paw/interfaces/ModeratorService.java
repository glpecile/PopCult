package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyIsModException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.User;

public interface ModeratorService {
    PageContainer<User> getModerators(int page, int pageSize);

    void promoteToMod(User user);

    void removeMod(User user);

    PageContainer<User> getModRequesters(int page, int pageSize);

    void addModRequest(User user) throws UserAlreadyIsModException, ModRequestAlreadyExistsException;

    void removeRequest(User user);

    boolean principalIsMod();
}
