package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.image.Image;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String username, String password, String name) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;

    Optional<User> changePassword(int userId, String currentPassword, String newPassword) throws InvalidCurrentPasswordException;

    void forgotPassword(String email) throws EmailNotExistsException;

    boolean resetPassword(Token token, String newPassword);

    Optional<User> getCurrentUser();

    boolean confirmRegister(Token token);

    void resendToken(String token);

    Optional<Image> getUserProfileImage(int imageId) throws ImageConversionException;

    void uploadUserProfileImage(int userId, byte[] photoBlob);

    void updateUserData(int userId, String email, String username, String name);
}
