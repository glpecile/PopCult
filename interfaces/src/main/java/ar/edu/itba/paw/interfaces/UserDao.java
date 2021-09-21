package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String userName, String password, String name, boolean enabled);

    void confirmRegister(int userId, boolean enabled);
}
