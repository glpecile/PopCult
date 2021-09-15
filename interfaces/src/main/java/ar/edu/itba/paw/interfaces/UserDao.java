package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    User register(String email, String userName, String password, String name, String profilePhotoURL);

    void addMediaToFav(int mediaId, int userId);

    void deleteMediaFromFav(int mediaId, int userId);

    boolean isFavorite(int mediaId, int userId);

    List<Integer> getUserFavoriteMedia(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteMediaCount(int userId);

    void addListToFav(int userId, int mediaListId);

    void deleteListFromFav(int userId, int mediaListId);

    boolean isFavoriteList(int userId, int mediaListId);

    List<Integer> getUserFavoriteLists(int userId, int page, int pageSize);

    Optional<Integer> getFavoriteListsCount(int userId);

}
