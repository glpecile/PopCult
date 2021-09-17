package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;

import java.util.Date;
import java.util.Optional;

public interface VerificationTokenDao {
    void createVerificationToken(int userId, String token, Date expiryDate);

    Optional<Token> getToken(String token);

    void deleteToken(Token token);

    void renewToken(String token, Date newExpiryDate);
}
