package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getById(int userId);

    List<User> getById (List<Integer> userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    List<User> getByUsernames(List<String> usernames);

    User register(String email, String username, String password, String name) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;

    Token createVerificationToken(User user) throws EmailAlreadyVerifiedException;

    void deleteUser(User user);

    Optional<User> changePassword(User user, String currentPassword, String newPassword) throws InvalidCurrentPasswordException;

    Token forgotPassword(User user);

    void resetPassword(Token token, String newPassword) throws InvalidTokenException;

    Optional<User> getCurrentUser();

    User confirmRegister(Token token) throws InvalidTokenException;

    Optional<Image> getUserProfileImage(int imageId) throws ImageConversionException;

    void uploadUserProfileImage(User user, byte[] photoBlob, String format) throws ImageConversionException;

    void deleteUserProfileImage(User user);

    void updateUserData(User user, String name);

    void strikeUser(User user);

    void banUser(User user);

    void unbanUser(User user);

    void unbanUsers(); // DO NOT DELETE

    PageContainer<User> getUsers(int page, int pageSize, UserRole userRole, Boolean banned, String term, Integer notInListId);
}
