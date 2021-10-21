package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String userName, String password, String name, boolean enabled, int role) throws EmailAlreadyExistsException, UsernameAlreadyExistsException;

    Optional<User> changePassword(int userId, String password);

    Optional<User> confirmRegister(int userId, boolean enabled);

    void updateUserProfileImage(int userId, int imageId);

    void updateUserData(int userId, String email, String username, String name);
}
