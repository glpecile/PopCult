package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyIsModException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface ModeratorService {
    PageContainer<User> getModerators(int page, int pageSize);

    void promoteToMod(User user);

    void removeMod(User user);

    Optional<ModRequest> getModRequest(int modRequestId);

    PageContainer<ModRequest> getModRequests(int page, int pageSize);

    ModRequest addModRequest(User user) throws UserAlreadyIsModException, ModRequestAlreadyExistsException;

    void removeModRequest(ModRequest modRequest);

    void removeRequest(User user);

    boolean principalIsMod();
}
