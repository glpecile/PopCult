package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.User;

public interface ModeratorService {
    PageContainer<User> getModerators(int page, int pageSize);

    void promoteToMod(int userId);

    void removeMod(int userId);

    PageContainer<User> getModRequesters(int page, int pageSize);

    void addModRequest(int userId);

    void removeRequest(int userId);
}
