package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;

import java.util.Date;
import java.util.Optional;

public interface VerificationTokenService {
    String createVerificationToken(int userId);

    Optional<Token> getToken(String token);

    void deleteToken(Token token);

    boolean isValidToken(Token token);

    void renewToken(String token);
}
