package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface TokenService {
    Token createToken(User user, TokenType type);

    Optional<Token> getToken(String token);

    void deleteToken(Token token);

    boolean isValidToken(Token token, TokenType type);

    void renewToken(Token token);
}
