package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    /* default */ static final boolean ENABLED_USER = true;
    /* default */ static final String DEFAULT_PROFILE_IMAGE_PATH = "/images/profile.jpeg";

    private static final int FIRST_BAN_STRIKES = 3;
    private static final int SECOND_BAN_STRIKES = 6;
    private static final int THIRD_BAN_STRIKES = 9;
    private static final int BAN_DAYS = User.BAN_DAYS;

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

        emailService.sendVerificationEmail(user, token.getToken(), LocaleContextHolder.getLocale());

        return user;
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
        emailService.sendResetPasswordEmail(user, token.getToken(), LocaleContextHolder.getLocale());
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
            emailService.sendVerificationEmail(token.getUser(), token.getToken(), LocaleContextHolder.getLocale());
        } else if (token.getType() == TokenType.RESET_PASS) {
            emailService.sendResetPasswordEmail(token.getUser(), token.getToken(), LocaleContextHolder.getLocale());
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

    @Transactional(readOnly = true)
    @Override
    public PageContainer<User> getBannedUsers(int page, int pageSize) {
        return userDao.getBannedUsers(page, pageSize);
    }

    @Transactional
    @Override
    public void strikeUser(User user) {
        int userStrikes = user.addStrike();
        switch(userStrikes) {
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
            if(user.getUnbanDate() != null && user.getUnbanDate().isBefore(actualDate)) {
                unbanUser(user);
                LOGGER.info("{} unbanned", user.getUsername());
            }
        });
    }

    @Transactional
    @Override
    public PageContainer<User> getUsers(int page, int pageSize){
        return userDao.getUsers(page, pageSize);
    }

}
