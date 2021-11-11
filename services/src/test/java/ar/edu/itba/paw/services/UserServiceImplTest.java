package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.InvalidCurrentPasswordException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private User user;
    private static final int USER_ID = 1;
    private static final String EMAIL = "test@popcult.com";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "password";
    private static final String NAME = "PopCult Test";

    private static final String TOKEN = "Token";
    private static final TokenType TOKEN_TYPE = TokenType.VERIFICATION;
    private static final LocalDateTime EXPIRY_DATE = LocalDateTime.now();


    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();
    @Mock
    private UserDao mockDao;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private ImageServiceImpl mockImageService;
    @Mock
    private EmailServiceImpl mockEmailService;
    @Mock
    private TokenServiceImpl mockTokenService;

    @Before
    public void setup() {
        user = new User.Builder(EMAIL, USERNAME, PASSWORD, NAME).userId(USER_ID).build();
    }

    @Test
    public void testRegister() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        when(mockDao.register(eq(EMAIL), eq(USERNAME), eq(PASSWORD), eq(NAME)))
                .thenReturn(user);
        when(mockPasswordEncoder.encode(Mockito.anyString())).thenReturn(PASSWORD);
        when(mockTokenService.createToken(any(), any()))
                .thenReturn(new Token(user, TOKEN_TYPE, TOKEN, EXPIRY_DATE));
        doNothing().when(mockEmailService).sendVerificationEmail(any(), anyString());

        User user = userService.register(EMAIL, USERNAME, PASSWORD, NAME);

        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
    }

    @Test(expected = InvalidCurrentPasswordException.class)
    public void testIncorrectChangePassword() throws InvalidCurrentPasswordException {
        when(mockPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        userService.changePassword(user, "incorrectPassword", "newPassword");

        Assert.fail();
    }


}
