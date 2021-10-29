package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String username, String password, String name) throws EmailAlreadyExistsException, UsernameAlreadyExistsException;

}
