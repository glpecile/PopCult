package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageService imageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    /* default */ static final boolean NOT_ENABLED_USER = false;
    /* default */ static final boolean ENABLED_USER = true;
    /* default */ static final String DEFAULT_PROFILE_IMAGE_PATH = "/images/profile.jpeg";
    /* default */ static final int DEFAULT_USER_ROLE = Roles.USER.ordinal();

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getById(int userId) {
        return userDao.getById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Transactional
    @Override
    public User register(String email, String username, String password, String name) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        User user = userDao.register(email, username, passwordEncoder.encode(password), name, NOT_ENABLED_USER, DEFAULT_USER_ROLE);

        String token = tokenService.createToken(user.getUserId(), TokenType.VERIFICATION.ordinal());

        emailService.sendVerificationEmail(user, token);

        return user;
    }

    @Transactional
    @Override
    public Optional<User> changePassword(int userId, String currentPassword, String newPassword) throws InvalidCurrentPasswordException {
        Optional<User> user = userDao.getById(userId);
        if(user.isPresent() && !passwordEncoder.matches(currentPassword, user.get().getPassword())) {
            LOGGER.error("userId: {} changing password failed.", userId);
            throw new InvalidCurrentPasswordException();
        }
        return userDao.changePassword(userId, passwordEncoder.encode(newPassword));
    }

    @Transactional
    @Override
    public void forgotPassword(String email) throws EmailNotExistsException {
        User user = getByEmail(email).orElseThrow(EmailNotExistsException::new);
        String token = tokenService.createToken(user.getUserId(), TokenType.RESET_PASS.ordinal());
        emailService.sendResetPasswordEmail(user, token);
    }

    @Transactional
    @Override
    public boolean resetPassword(Token token, String newPassword) {
        boolean isValidToken = tokenService.isValidToken(token, TokenType.RESET_PASS.ordinal());
        if (isValidToken) {
            userDao.changePassword(token.getUserId(), passwordEncoder.encode(newPassword));
            tokenService.deleteToken(token);
        }
        return isValidToken;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getByUsername(userDetails.getUsername());
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public boolean confirmRegister(Token token) {
        boolean isValidToken = tokenService.isValidToken(token, TokenType.VERIFICATION.ordinal());
        if (isValidToken) {
            userDao.confirmRegister(token.getUserId(), ENABLED_USER).ifPresent(this::authWithoutPassword);
            tokenService.deleteToken(token);
        }
        return isValidToken;
    }

    private void authWithoutPassword(User user) {
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Roles.values()[user.getRole()].getRoleType()));
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Image> getUserProfileImage(int imageId) throws ImageConversionException {
        if(imageId == User.DEFAULT_IMAGE) {
            return imageService.getImage(DEFAULT_PROFILE_IMAGE_PATH);
        }
        return imageService.getImage(imageId);
    }

    @Transactional
    @Override
    public void uploadUserProfileImage(int userId, byte[] photoBlob) {
        imageService.uploadImage(photoBlob).ifPresent(image -> {
            userDao.updateUserProfileImage(userId, image.getImageId());
        });
    }

    @Transactional
    @Override
    public void updateUserData(int userId, String email, String username, String name) {
        userDao.updateUserData(userId, email, username, name);
    }
}
