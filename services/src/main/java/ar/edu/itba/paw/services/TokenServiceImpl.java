package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TokenDao;
import ar.edu.itba.paw.interfaces.TokenService;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenDao tokenDao;

    /* default */ static final int EXPIRATION = 1; //days

    @Transactional
    @Override
    public Token createToken(User user, TokenType type) {
        String token = UUID.randomUUID().toString();
        return tokenDao.createToken(user, type, token, calculateExpiryDate(EXPIRATION));
    }

    private LocalDateTime calculateExpiryDate(int expiryTimeInDays) {
        return LocalDateTime.now().plusDays(expiryTimeInDays);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Token> getToken(String token) {
        return tokenDao.getToken(token);
    }

    @Transactional
    @Override
    public void deleteToken(Token token) {
        tokenDao.deleteToken(token);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isValidToken(Token token, TokenType type) {
        return token.getType() == type && token.getExpiryDate().isAfter(LocalDateTime.now());
    }

    @Transactional
    @Override
    public void renewToken(Token token) {
        token.setExpiryDate(calculateExpiryDate(EXPIRATION));
    }
}