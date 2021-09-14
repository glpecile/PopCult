package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getById(int userId) {
        return userDao.getById(userId);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User register(String email, String userName, String password, String name, String profilePhotoURL) {
        return userDao.register(email, userName, passwordEncoder.encode(password), name, profilePhotoURL);
    }
}
