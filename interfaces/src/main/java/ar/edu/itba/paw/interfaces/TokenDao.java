package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;

import java.util.Date;
import java.util.Optional;

public interface TokenDao {
    void createToken(int userId, int type, String token, Date expiryDate);

    Optional<Token> getToken(String token);

    void deleteToken(Token token);

    void renewToken(String token, Date newExpiryDate);
}
