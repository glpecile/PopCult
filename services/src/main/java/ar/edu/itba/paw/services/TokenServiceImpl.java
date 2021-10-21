package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TokenDao;
import ar.edu.itba.paw.interfaces.TokenService;
import ar.edu.itba.paw.models.user.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenDao tokenDao;

    /* default */ static final int EXPIRATION = 60 * 24;

    @Transactional
    @Override
    public String createToken(int userId, int type) {
        String token = UUID.randomUUID().toString();
        tokenDao.createToken(userId, type, token, calculateExpiryDate(EXPIRATION));
        return token;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
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
    public boolean isValidToken(Token token, int type) {
        Calendar calendar = Calendar.getInstance();
        return token.getType() == type && token.getExpiryDate().getTime() >= calendar.getTime().getTime();
    }

    @Transactional
    @Override
    public void renewToken(String token) {
        tokenDao.renewToken(token, calculateExpiryDate(EXPIRATION));
    }
}
