package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getById(int userId);

    List<User> getById (List<Integer> userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String username, String password, String name) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;

    void deleteUser(User user);

    Optional<User> changePassword(User user, String currentPassword, String newPassword) throws InvalidCurrentPasswordException;

    void forgotPassword(String email) throws EmailNotExistsException;

    boolean resetPassword(Token token, String newPassword);

    Optional<User> getCurrentUser();

    boolean confirmRegister(Token token);

    void resendToken(Token token);

    Optional<Image> getUserProfileImage(int imageId) throws ImageConversionException;

    void uploadUserProfileImage(User user, byte[] photoBlob);

    void updateUserData(User user, String name);
}
