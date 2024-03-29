package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    /* default */ static final boolean ENABLED_USER = true;
    /* default */ static final String DEFAULT_PROFILE_IMAGE_PATH = "/images/profile.jpeg";
    private static final int WIDTH = 160;
    private static final int HEIGHT = 160;

    private static final int FIRST_BAN_STRIKES = 3;
    private static final int SECOND_BAN_STRIKES = 6;
    private static final int THIRD_BAN_STRIKES = 9;

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

    @Override
    public List<User> getByUsernames(List<String> usernames) {
        return userDao.getByUsernames(usernames);
    }

    @Transactional
    @Override
    public User register(String email, String username, String password, String name) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        User user = userDao.register(email, username, passwordEncoder.encode(password), name);
        try {
            createVerificationToken(user);
        } catch (EmailAlreadyVerifiedException ignored) {
            // Never thrown
        }
        return user;
    }

    @Transactional
    @Override
    public Token createVerificationToken(User user) throws EmailAlreadyVerifiedException {
        if (user.isEnabled()) {
            throw new EmailAlreadyVerifiedException();
        }
        Token token = tokenService.createToken(user, TokenType.VERIFICATION);
        emailService.sendVerificationEmail(user, token.getToken(), LocaleContextHolder.getLocale());
        return token;
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
        emailService.sendDeletedUserEmail(user, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public Optional<User> changePassword(User user, String currentPassword, String newPassword) throws InvalidCurrentPasswordException {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            LOGGER.error("userId: {} changing password failed. Incorrect password", user.getUserId());
            throw new InvalidCurrentPasswordException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return Optional.of(user);
    }

    @Transactional
    @Override
    public Token forgotPassword(User user) {
        Token token = tokenService.createToken(user, TokenType.RESET_PASS);
        emailService.sendResetPasswordEmail(user, token.getToken(), LocaleContextHolder.getLocale());
        return token;
    }

    @Transactional
    @Override
    public void resetPassword(Token token, String newPassword) throws InvalidTokenException {
        boolean isValidToken = tokenService.isValidToken(token, TokenType.RESET_PASS);
        if (!isValidToken) {
            throw new InvalidTokenException();
        }
        token.getUser().setPassword(passwordEncoder.encode(newPassword));
        tokenService.deleteToken(token);
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return Optional.empty();
        return getByUsername(auth.getName());
    }

    @Transactional
    @Override
    public User confirmRegister(Token token) throws InvalidTokenException {
        boolean isValidToken = tokenService.isValidToken(token, TokenType.VERIFICATION);
        if (!isValidToken) {
            throw new InvalidTokenException();
        }
        final User user = token.getUser();
        user.setEnabled(ENABLED_USER);
        tokenService.deleteToken(token);
        return user;
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
    public void uploadUserProfileImage(User user, byte[] photoBlob, String format) throws ImageConversionException {
        final Image image = imageService.uploadImage(photoBlob, WIDTH, HEIGHT, format);
        if (user.getImage() != null) {
            imageService.deleteImage(user.getImage());
        }
        user.setImage(image);
    }

    @Transactional
    @Override
    public void deleteUserProfileImage(User user) {
        if (user.getImage() != null) {
            imageService.deleteImage(user.getImage());
            user.setImage(null);
        }
    }

    @Transactional
    @Override
    public void updateUserData(User user, String name) {
        user.setName(name);
    }

    @Transactional
    @Override
    public void strikeUser(User user) {
        int userStrikes = user.addStrike();
        switch (userStrikes) {
            case FIRST_BAN_STRIKES:
            case SECOND_BAN_STRIKES:
                banUser(user);
                break;
            case THIRD_BAN_STRIKES:
                userDao.deleteUser(user);
                emailService.sendDeletedUserByStrikesEmail(user, LocaleContextHolder.getLocale());
                break;
        }
    }

    @Transactional
    @Override
    public void banUser(User user) {
        user.setNonLocked(false);
        user.setBanDate(LocalDateTime.now());
        emailService.sendBannedUserEmail(user, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public void unbanUser(User user) {
        user.setNonLocked(true);
        user.setBanDate(null);
        emailService.sendUnbannedUserEmail(user, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void unbanUsers() {
        LOGGER.info("Executing scheduled tasks");
        LocalDateTime actualDate = LocalDateTime.now();
        userDao.getBannedUsers().forEach(user -> {
            LOGGER.info("Checking ban for {}", user.getUsername());
            if (user.getUnbanDate() != null && user.getUnbanDate().isBefore(actualDate)) {
                unbanUser(user);
                LOGGER.info("{} unbanned", user.getUsername());
            }
        });
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<User> getUsers(int page, int pageSize, UserRole userRole, Boolean banned, String term, Integer notInListId) {
        return userDao.getUsers(page, pageSize, userRole, banned, term, notInListId);
    }

}
