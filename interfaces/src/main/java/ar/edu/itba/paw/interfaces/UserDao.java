package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    User register(String email, String userName, String password, String name, String profilePhotoURL);
}
