package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String username, String password, String name);

    Optional<User> getCurrentUser();

    boolean confirmRegister(Token token);

    void resendVerificationEmail(String token);
}
