package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.InvalidCurrentPasswordException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
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

import java.util.Date;
import java.util.Optional;

import static ar.edu.itba.paw.services.UserServiceImpl.DEFAULT_USER_ROLE;
import static ar.edu.itba.paw.services.UserServiceImpl.NOT_ENABLED_USER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final int USER_ID = 1;
    private static final String EMAIL = "test@popcult.com";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "password";
    private static final String NAME = "PopCult Test";
    private static final String TOKEN = "Token";
    private static final int TOKEN_TYPE = TokenType.VERIFICATION.ordinal();
    private static final Date EXPIRY_DATE = new Date();


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

    @Test
    public void testRegister() throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        //1 - Setup - Preconditions
        when(mockDao.register(eq(EMAIL), eq(USERNAME), eq(PASSWORD), eq(NAME),
                eq(NOT_ENABLED_USER), eq(DEFAULT_USER_ROLE)))
                .thenReturn(new User(USER_ID, EMAIL, USERNAME, PASSWORD, NAME, NOT_ENABLED_USER, null, DEFAULT_USER_ROLE));
        when(mockPasswordEncoder.encode(Mockito.anyString())).thenReturn(PASSWORD);

        //2 -Try class under test
        User user = userService.register(EMAIL, USERNAME, PASSWORD, NAME);

        //3 - Asserts - Postconditions
        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
    }

    @Test(expected = InvalidCurrentPasswordException.class)
    public void testIncorrectChangePassword() throws InvalidCurrentPasswordException {
        //1 - Setup
        when(mockDao.getById(eq(USER_ID)))
                .thenReturn(Optional.of(new User(USER_ID, EMAIL, USERNAME, PASSWORD, NAME, NOT_ENABLED_USER, null, DEFAULT_USER_ROLE)));
        when(mockPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        //2
        userService.changePassword(USER_ID, "incorrectPassword", "newPassword");

        //3 - Asserts
        Assert.fail();
    }

}
