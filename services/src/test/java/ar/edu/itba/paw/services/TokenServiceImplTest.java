package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static ar.edu.itba.paw.services.TokenServiceImpl.EXPIRATION;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest {
    private static final int USER_ID = 1;
    private static final String TOKEN = "Token";
    private static final int TOKEN_TYPE = TokenType.VERIFICATION.ordinal();
    private static Date EXPIRY_DATE;

    @InjectMocks
    private final TokenServiceImpl tokenService = new TokenServiceImpl();

    @Test
    public void testIsValidToken() {
        //1 - Setup
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, EXPIRATION);
        EXPIRY_DATE = new Date(calendar.getTime().getTime());
        Token token = new Token(USER_ID, TOKEN_TYPE, TOKEN, EXPIRY_DATE);

        //2
        boolean isValid = tokenService.isValidToken(token, TOKEN_TYPE);

        //3 - Asserts
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsInvalidToken() {
        //1 - Setup
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, -EXPIRATION);
        EXPIRY_DATE = new Date(calendar.getTime().getTime());
        Token token = new Token(USER_ID, TOKEN_TYPE, TOKEN, EXPIRY_DATE);

        //2
        boolean isValid = tokenService.isValidToken(token, TOKEN_TYPE);

        //3 - Asserts
        Assert.assertFalse(isValid);
    }
}
