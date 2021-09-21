package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private VerificationTokenService verificationTokenService;

    private final MessageSource messageSource;

    private static final boolean NOT_ENABLED_USER = false;
    private static final boolean ENABLED_USER = true;

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
        User user = userDao.register(email, username, passwordEncoder.encode(password), name, NOT_ENABLED_USER);

        String token = verificationTokenService.createVerificationToken(user.getUserId());

        sendVerificationEmail(email, username, token);

        return user;
    }

    private void sendVerificationEmail(String email, String username, String token) {
        final Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("username", username); //TODO swap to full name
        mailMap.put("token", token);
        final String subject = messageSource.getMessage("email.confirmation.subject", null, Locale.getDefault());
        emailService.sendEmail(email, subject, "registerConfirmation.html", mailMap);
    }

    @Override
    public Optional<User> getCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getByUsername(userDetails.getUsername());
        }
        return Optional.empty();
    }

    @Override
    public boolean confirmRegister(Token token) {
        boolean isValidToken = verificationTokenService.isValidToken(token);
        if(isValidToken) {
            userDao.confirmRegister(token.getUserId(), ENABLED_USER);
            verificationTokenService.deleteToken(token);
        }
        return isValidToken;
    }

    @Override
    public void resendVerificationEmail(String token) {
        verificationTokenService.renewToken(token);
        verificationTokenService.getToken(token).ifPresent(validToken -> {
            getById(validToken.getUserId()).ifPresent(user -> {
                sendVerificationEmail(user.getEmail(), user.getUsername(), validToken.getToken());
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


}
