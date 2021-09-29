package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TokenDao;
import ar.edu.itba.paw.interfaces.TokenService;
import ar.edu.itba.paw.models.user.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenDao tokenDao;

    private static final int EXPIRATION = 60 * 24;

    @Override
    public String createVerificationToken(int userId) {
        String token = UUID.randomUUID().toString();
        tokenDao.createVerificationToken(userId, token, calculateExpiryDate(EXPIRATION));
        return token;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    @Override
    public Optional<Token> getToken(String token) {
        return tokenDao.getToken(token);
    }

    @Override
    public void deleteToken(Token token) {
        tokenDao.deleteToken(token);
    }

    @Override
    public boolean isValidToken(Token token) {
        Calendar calendar = Calendar.getInstance();
        return token.getExpiryDate().getTime() > calendar.getTime().getTime();
    }

    @Override
    public void renewToken(String token) {
        tokenDao.renewToken(token, calculateExpiryDate(EXPIRATION));
    }
}
