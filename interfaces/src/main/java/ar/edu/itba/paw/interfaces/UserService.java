package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getById(int userId);

    Optional<User> getByEmail(String email);

    User register(String email, String userName, String password, String name, String profilePhotoURL);

    User getCurrentUser();

    void addMediaToFav(int mediaId);

    void deleteMediaFromFav(int mediaId);

    boolean isFavorite(int mediaId);

    List<Integer> getUserFavoriteMedia(int page, int pageSize);

    Optional<Integer> getFavoriteMediaCount();

    void addListToFav(int mediaListId);

    void deleteListFromFav(int mediaListId);

    boolean isFavoriteList(int mediaListId);

    List<Integer> getUserFavoriteLists(int page, int pageSize);

    Optional<Integer> getFavoriteListsCount();
}
