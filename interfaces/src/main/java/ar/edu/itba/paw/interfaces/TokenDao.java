package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface TokenDao {
    Token createToken(User user, TokenType type, String token, LocalDateTime expiryDate);

    Optional<Token> getToken(String token);

    void deleteToken(Token token);
}
