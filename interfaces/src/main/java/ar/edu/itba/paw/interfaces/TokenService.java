package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;

import java.util.Date;
import java.util.Optional;

public interface TokenService {
    String createToken(int userId, int type);

    Optional<Token> getToken(String token);

    void deleteToken(Token token);

    boolean isValidToken(Token token, int type);

    void renewToken(String token);
}
