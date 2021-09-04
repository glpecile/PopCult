package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getById(int userId);
}
