package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.interfaces.FavoriteService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        favoriteDao.deleteMediaFromFav(mediaId, userId);
    }

    @Override
    public boolean isFavorite(int mediaId, int userId) {
        return favoriteDao.isFavorite(mediaId, userId);
    }

    @Override
    public PageContainer<Media> getUserFavoriteMedia(int userId, int page, int pageSize) {
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
        favoriteDao.deleteListFromFav(mediaListId, userId);
    }

    @Override
    public boolean isFavoriteList(int mediaListId, int userId) {
        return favoriteDao.isFavoriteList(mediaListId, userId);
    }

    @Override
    public PageContainer<MediaList> getUserFavoriteLists(int userId, int page, int pageSize) {
        return favoriteDao.getUserFavoriteLists(userId, page, pageSize);
    }

    @Override
    public PageContainer<MediaList> getUserPublicFavoriteLists(int userId, int page, int pageSize) {
        return favoriteDao.getUserPublicFavoriteLists(userId, page, pageSize);
    }

    @Override
    public Optional<Integer> getFavoriteListsCount(int userId) {
        return favoriteDao.getFavoriteListsCount(userId);
    }

    @Override
    public PageContainer<MediaList> getRecommendationsBasedOnFavLists(int userId, int page, int pageSize) {
        PageContainer<MediaList> recommendations = favoriteDao.getRecommendationsBasedOnFavLists(userId, page, pageSize);
        if (recommendations.getTotalCount() < pageSize) {
            PageContainer<MediaList> filler = favoriteDao.getMostLikedLists(userId,0, pageSize * 2);
            Set<MediaList> toAdd = new HashSet<>(recommendations.getElements());
            for(MediaList list: filler.getElements()){
                toAdd.add(list);
                if(toAdd.size()==pageSize) break;
            }
            return new PageContainer<>(new ArrayList<>(toAdd), page, pageSize, recommendations.getTotalPages() + filler.getTotalPages());
        }
        return recommendations;
    }

    @Override
    public PageContainer<MediaList> getMostLikedLists(int page, int pageSize) {
        return favoriteDao.getMostLikedLists(page, pageSize);
    }

    @Override
    public PageContainer<Media> getRecommendationsBasedOnFavMedia(int mediaType, int userId, int page, int pageSize) {
        PageContainer<Media> recommendations = favoriteDao.getRecommendationsBasedOnFavMedia(mediaType, userId, page, pageSize);
        if (recommendations.getTotalCount() < pageSize) {
            PageContainer<Media> filler = favoriteDao.getMostLikedMedia(mediaType,0, pageSize * 2);
            Set<Media> toAdd = new HashSet<>(recommendations.getElements());
            for(Media media: filler.getElements()){
                toAdd.add(media);
                if(toAdd.size()==pageSize) break;
            }
            return new PageContainer<>(new ArrayList<>(toAdd), page, pageSize, recommendations.getTotalPages() + filler.getTotalPages());
        }
        return recommendations;
    }

    @Override
    public PageContainer<Media> getMostLikedMedia(int page, int pageSize) {
        return favoriteDao.getMostLikedMedia(page, pageSize);
    }

    @Override
    public PageContainer<Media> getMostLikedMedia(int mediaType, int page, int pageSize) {
        return favoriteDao.getMostLikedMedia(mediaType, page, pageSize);
    }
}
