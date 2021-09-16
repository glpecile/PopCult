package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.interfaces.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDao favoriteDao;


    @Override
    public void addMediaToFav(int mediaId, int userId) {
        favoriteDao.addMediaToFav(mediaId, userId);
    }

    @Override
    public void deleteMediaFromFav(int mediaId, int userId) {
        favoriteDao.deleteListFromFav(mediaId, userId);
    }

    @Override
    public boolean isFavorite(int mediaId, int userId) {
        return favoriteDao.isFavorite(mediaId, userId);
    }

    @Override
    public List<Integer> getUserFavoriteMedia(int userId, int page, int pageSize) {
        return favoriteDao.getUserFavoriteMedia(userId, page, pageSize);
    }

    @Override
    public Optional<Integer> getFavoriteMediaCount(int userId) {
        return favoriteDao.getFavoriteMediaCount(userId);
    }

    @Override
    public void addListToFav(int mediaListId, int userId) {
        favoriteDao.addListToFav(mediaListId, userId);
    }

    @Override
    public void deleteListFromFav(int mediaListId, int userId) {
        favoriteDao.deleteMediaFromFav(mediaListId, userId);
    }

    @Override
    public boolean isFavoriteList(int mediaListId, int userId) {
        return favoriteDao.isFavoriteList(mediaListId, userId);
    }

    @Override
    public List<Integer> getUserFavoriteLists(int userId, int page, int pageSize) {
        return favoriteDao.getUserFavoriteLists(userId, page, pageSize);
    }

    @Override
    public Optional<Integer> getFavoriteListsCount(int userId) {
        return favoriteDao.getFavoriteListsCount(userId);
    }
}
