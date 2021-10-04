package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.EmailNotExistsException;
import ar.edu.itba.paw.interfaces.exceptions.InvalidCurrentPasswordException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    private final MessageSource messageSource;

    private static final boolean NOT_ENABLED_USER = false;
    private static final boolean ENABLED_USER = true;
    private static final int DEFAULT_IMAGE_ID = 1;
    private static final int DEFAULT_USER_ROLE = Roles.USER.ordinal();

    @Autowired
    public UserServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Optional<User> getById(int userId) {
        return userDao.getById(userId);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User register(String email, String username, String password, String name) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        User user = userDao.register(email, username, passwordEncoder.encode(password), name, NOT_ENABLED_USER, DEFAULT_IMAGE_ID, DEFAULT_USER_ROLE);

        String token = tokenService.createToken(user.getUserId(), TokenType.VERIFICATION.ordinal());

        emailService.sendVerificationEmail(user, token);

        return user;
    }

    @Override
    public Optional<User> changePassword(int userId, String currentPassword, String newPassword) {
        userDao.getById(userId).ifPresent(user -> {
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new InvalidCurrentPasswordException();
            }
        });
        return userDao.changePassword(userId, passwordEncoder.encode(newPassword));
    }

    @Override
    public void forgotPassword(String email) {
        User user = getByEmail(email).orElseThrow(EmailNotExistsException::new);
        String token = tokenService.createToken(user.getUserId(), TokenType.RESET_PASS.ordinal());
        emailService.sendResetPasswordEmail(user, token);
    }

    @Override
    public boolean resetPassword(Token token, String newPassword) {
        boolean isValidToken = tokenService.isValidToken(token, TokenType.RESET_PASS.ordinal());
        if (isValidToken) {
            userDao.changePassword(token.getUserId(), passwordEncoder.encode(newPassword));
            tokenService.deleteToken(token);
        }
        return isValidToken;
    }

    @Override
    public Optional<User> getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getByUsername(userDetails.getUsername());
        }
        return Optional.empty();
    }

    @Override
    public boolean confirmRegister(Token token) {
        boolean isValidToken = tokenService.isValidToken(token, TokenType.VERIFICATION.ordinal());
        if (isValidToken) {
            userDao.confirmRegister(token.getUserId(), ENABLED_USER);
            tokenService.deleteToken(token);
        }
        return isValidToken;
    }

    @Override
    public void resendToken(String token) {
        tokenService.renewToken(token);
        tokenService.getToken(token).ifPresent(validToken -> {
            getById(validToken.getUserId()).ifPresent(user -> {
                if (validToken.getType() == TokenType.VERIFICATION.ordinal()) {
                    emailService.sendVerificationEmail(user, validToken.getToken());
                } else if (validToken.getType() == TokenType.RESET_PASS.ordinal()) {
                    emailService.sendResetPasswordEmail(user, validToken.getToken());
                }
            });
        });
    }

    @Override
    public Optional<Image> getUserProfileImage(int imageId) {
        return imageService.getImage(imageId);
    }

    @Override
    public void uploadUserProfileImage(int userId, byte[] photoBlob, long imageContentLength, String imageContentType) {
        imageService.uploadImage(photoBlob, imageContentLength, imageContentType).ifPresent(image -> {
            userDao.updateUserProfileImage(userId, image.getImageId());
        });
    }

    @Override
    public void updateUserData(int userId, String email, String username, String name) {
        userDao.updateUserData(userId, email, username, name);
    }
}
