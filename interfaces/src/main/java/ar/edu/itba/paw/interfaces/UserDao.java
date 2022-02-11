package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getById(int userId);

    List<User> getById (List<Integer> userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String username, String password, String name) throws EmailAlreadyExistsException, UsernameAlreadyExistsException;

    void deleteUser(User user);

    PageContainer<User> getBannedUsers(int page, int pageSize);

    List<User> getBannedUsers();

    PageContainer<User> getUsers(int page, int pageSize, UserRole userRole, Boolean banned);
}
