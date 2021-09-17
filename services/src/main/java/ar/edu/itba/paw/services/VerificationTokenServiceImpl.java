package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    VerificationTokenDao verificationTokenDao;

    private static final int EXPIRATION = 60 * 24;

    @Override
    public String createVerificationToken(int userId) {
        String token = UUID.randomUUID().toString();
        verificationTokenDao.createVerificationToken(userId, token, calculateExpiryDate(EXPIRATION));
        return token;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
