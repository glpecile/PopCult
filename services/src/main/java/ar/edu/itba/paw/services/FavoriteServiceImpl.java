package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.interfaces.FavoriteService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    @Transactional
    @Override
    public void addMediaToFav(Media media, User user) {
        favoriteDao.addMediaToFav(media, user);
    }

    @Transactional
    @Override
    public void deleteMediaFromFav(Media media, User user) {
        favoriteDao.deleteMediaFromFav(media, user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isFavorite(Media media, User user) {
        return favoriteDao.isFavorite(media, user);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getUserFavoriteMedia(User user, int page, int pageSize) {
        return favoriteDao.getUserFavoriteMedia(user, page, pageSize);
    }


    @Transactional
    @Override
    public void addListToFav(MediaList mediaList, User user) {
        favoriteDao.addListToFav(mediaList, user);
    }

    @Transactional
    @Override
    public void deleteListFromFav(MediaList mediaList, User user) {
        favoriteDao.deleteListFromFav(mediaList, user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isFavoriteList(MediaList mediaList, User user) {
        return favoriteDao.isFavoriteList(mediaList, user);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getUserFavoriteLists(User user, int page, int pageSize) {
        return favoriteDao.getUserFavoriteLists(user, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getUserPublicFavoriteLists(User user, int page, int pageSize) {
        return favoriteDao.getUserPublicFavoriteLists(user, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getRecommendationsBasedOnFavLists(User user, int page, int pageSize) {
        PageContainer<MediaList> recommendations = favoriteDao.getRecommendationsBasedOnFavLists(user, page, pageSize);
        if (recommendations.getTotalCount() < pageSize) {
            PageContainer<MediaList> filler = favoriteDao.getMostLikedLists(user, 0, pageSize * 2);
            Set<MediaList> toAdd = new HashSet<>(recommendations.getElements());
            for(MediaList list: filler.getElements()){
                toAdd.add(list);
                if(toAdd.size()==pageSize) break;
            }
            return new PageContainer<>(new ArrayList<>(toAdd), page, pageSize, recommendations.getTotalPages() + filler.getTotalPages());
        }
        return recommendations;
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> getMostLikedLists(int page, int pageSize) {
        return favoriteDao.getMostLikedLists(page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getRecommendationsBasedOnFavMedia(MediaType mediaType, User user, int page, int pageSize) {
        PageContainer<Media> recommendations = favoriteDao.getRecommendationsBasedOnFavMedia(mediaType, user, page, pageSize);
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

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMostLikedMedia(int page, int pageSize) {
        return favoriteDao.getMostLikedMedia(page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMostLikedMedia(MediaType mediaType, int page, int pageSize) {
        return favoriteDao.getMostLikedMedia(mediaType, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public int getLikesFromList(MediaList mediaList) {
        return favoriteDao.getLikesFromList(mediaList);
    }

    @Transactional(readOnly = true)
    @Override
    public int getLikesFromMedia(Media media) {
        return favoriteDao.getLikesFromMedia(media);
    }
}
