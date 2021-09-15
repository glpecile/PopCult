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
    public User getCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getByEmail(userDetails.getUsername()).orElse(User.getDummyUser());
        }
        return User.getDummyUser();
    }

    @Override
    public void addMediaToFav(int mediaId) {
        User user = getCurrentUser();
        userDao.addMediaToFav(mediaId, user.getUserId());
    }

    @Override
    public void deleteMediaFromFav(int mediaId) {
        User user = getCurrentUser();
        userDao.deleteMediaFromFav(mediaId, user.getUserId());
    }

    @Override
    public boolean isFavorite(int mediaId) {
        return userDao.isFavorite(mediaId, getCurrentUser().getUserId());
    }

    @Override
    public List<Integer> getUserFavoriteMedia(int page, int pageSize) {
        User user = getCurrentUser();
        return userDao.getUserFavoriteMedia(user.getUserId(), page, pageSize);
    }

    @Override
    public Optional<Integer> getFavoriteMediaCount() {
        User user = getCurrentUser();
        return userDao.getFavoriteMediaCount(user.getUserId());
    }

    @Override
    public void addListToFav(int mediaListId) {
        User user = getCurrentUser();
        userDao.addListToFav(user.getUserId(), mediaListId);
    }

    @Override
    public void deleteListFromFav(int mediaListId) {
        User user = getCurrentUser();
        userDao.deleteListFromFav(user.getUserId(), mediaListId);
    }

    @Override
    public boolean isFavoriteList(int mediaListId) {
        User user = getCurrentUser();
        return userDao.isFavoriteList(user.getUserId(), mediaListId);
    }

    @Override
    public List<Integer> getUserFavoriteLists(int page, int pageSize) {
        User user = getCurrentUser();
        return userDao.getUserFavoriteLists(user.getUserId(), page, pageSize);
    }

    @Override
    public Optional<Integer> getFavoriteListsCount() {
        User user = getCurrentUser();
        return userDao.getFavoriteListsCount(user.getUserId());
    }
}
