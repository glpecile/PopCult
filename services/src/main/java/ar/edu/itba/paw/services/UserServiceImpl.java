package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.UserRole;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
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
    /* default */ static final UserRole DEFAULT_USER_ROLE = UserRole.USER;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getById(int userId) {
        return userDao.getById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getById(List<Integer> userId) {
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
        User user = userDao.register(email, username, passwordEncoder.encode(password), name);

        Token token = tokenService.createToken(user, TokenType.VERIFICATION);

        emailService.sendVerificationEmail(user, token.getToken());

        return user;
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
        emailService.sendDeletedUserEmail(user);
    }

    @Transactional
    @Override
    public Optional<User> changePassword(User user, String currentPassword, String newPassword) throws InvalidCurrentPasswordException {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            LOGGER.error("userId: {} changing password failed.", user.getUserId());
            throw new InvalidCurrentPasswordException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return Optional.of(user);
    }

    @Transactional
    @Override
    public void forgotPassword(String email) throws EmailNotExistsException {
        User user = getByEmail(email).orElseThrow(EmailNotExistsException::new);
        Token token = tokenService.createToken(user, TokenType.RESET_PASS);
        emailService.sendResetPasswordEmail(user, token.getToken());
    }

    @Transactional
    @Override
    public boolean resetPassword(Token token, String newPassword) {
        boolean isValidToken = tokenService.isValidToken(token, TokenType.RESET_PASS);
        if (isValidToken) {
            token.getUser().setPassword(passwordEncoder.encode(newPassword));
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
        boolean isValidToken = tokenService.isValidToken(token, TokenType.VERIFICATION);
        if (isValidToken) {
            final User user = token.getUser();
            user.setEnabled(ENABLED_USER);
            authWithoutPassword(user);
            tokenService.deleteToken(token);
        }
        return isValidToken;
    }

    private void authWithoutPassword(User user) {
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleType()));
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional
    @Override
    public void resendToken(Token token) {
        tokenService.renewToken(token);
        if (token.getType() == TokenType.VERIFICATION) {
            emailService.sendVerificationEmail(token.getUser(), token.getToken());
        } else if (token.getType() == TokenType.RESET_PASS) {
            emailService.sendResetPasswordEmail(token.getUser(), token.getToken());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Image> getUserProfileImage(int imageId) throws ImageConversionException {
        if (imageId == User.DEFAULT_IMAGE) {
            return imageService.getImage(DEFAULT_PROFILE_IMAGE_PATH);
        }
        return imageService.getImage(imageId);
    }

    @Transactional
    @Override
    public void uploadUserProfileImage(User user, byte[] photoBlob) {
        final Image image = imageService.uploadImage(photoBlob);
        user.setImage(image);
    }

    @Transactional
    @Override
    public void updateUserData(User user, String name) {
        user.setName(name);
    }
}
