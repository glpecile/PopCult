package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TokenDao;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static ar.edu.itba.paw.services.TokenServiceImpl.EXPIRATION;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest {

    private User user;
    private static final String EMAIL = "test@popcult.com";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "password";
    private static final String NAME = "PopCult Test";

    private static final String TOKEN = "Token";
    private static final TokenType TOKEN_TYPE = TokenType.VERIFICATION;

    private static LocalDateTime expiryDate;

    @InjectMocks
    private final TokenServiceImpl tokenService = new TokenServiceImpl();
    @Mock
    private TokenDao mockDao;

    @Before
    public void setup() {
        user = new User.Builder(EMAIL, USERNAME, PASSWORD, NAME).build();
    }

    @Test
    public void testCreateToken() {
        expiryDate = LocalDateTime.now().plusDays(EXPIRATION);
        when(mockDao.createToken(eq(user), eq(TOKEN_TYPE), anyString(), any()))
                .thenReturn(new Token(user, TOKEN_TYPE, TOKEN, expiryDate));

        Token token = tokenService.createToken(user, TOKEN_TYPE);

        Assert.assertNotNull(token);
        Assert.assertEquals(TOKEN, token.getToken());
    }

    @Test
    public void testIsValidToken() {
        expiryDate = LocalDateTime.now().plusDays(EXPIRATION);
        Token token = new Token(user, TOKEN_TYPE, TOKEN, expiryDate);

        boolean isValid = tokenService.isValidToken(token, TOKEN_TYPE);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsInvalidToken() {
        expiryDate = LocalDateTime.now().plusDays(-EXPIRATION);
        Token token = new Token(user, TOKEN_TYPE, TOKEN, expiryDate);

        boolean isValid = tokenService.isValidToken(token, TOKEN_TYPE);

        Assert.assertFalse(isValid);
    }
}
