package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Optional<User> getCurrentUser() {
        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getByEmail(userDetails.getUsername());
    }

    @Override
    public void addMediaToFav(int mediaId) {
        User user = getCurrentUser().orElseThrow(RuntimeException::new);
        userDao.addMediaToFav(mediaId, user.getUserId());
    }

    @Override
    public void deleteMediaFromFav(int mediaId) {
        User user = getCurrentUser().orElseThrow(RuntimeException::new);
        userDao.deleteMediaFromFav(mediaId, user.getUserId());
    }

    @Override
    public boolean isFavorite(int mediaId) {
        User user = getCurrentUser().orElseThrow(RuntimeException::new);
        return userDao.isFavorite(mediaId, user.getUserId());
    }

    @Override
    public List<Integer> getUserFavoriteMedia(int page, int pageSize) {
        User user = getCurrentUser().orElseThrow(RuntimeException::new);
        return userDao.getUserFavoriteMedia(user.getUserId(), page, pageSize);
    }

    @Override
    public Optional<Integer> getFavoriteMediaCount() {
        User user = getCurrentUser().orElseThrow(RuntimeException::new);
        return userDao.getFavoriteMediaCount(user.getUserId());
    }
}
