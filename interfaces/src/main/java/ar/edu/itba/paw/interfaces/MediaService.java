package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface MediaService {

    Optional<Media> getById(int mediaId);

    List<Media> getById(List<Integer> mediaIds);

    PageContainer<Media> getMediaList(int page, int pageSize);

    PageContainer<Media> getMediaList(int mediaType, int page, int pageSize);

    Optional<Integer> getMediaCount();

    Optional<Integer> getMediaCountByMediaType(int mediaType);

    PageContainer<Media> getLatestMediaList(int mediaType, int page, int pageSize);

    List<Media> searchMediaByTitle(String title, int page, int pageSize);

    Optional<Integer> getCountSearchMediaByTitle(String title);

    PageContainer<Media> getMostLikedMedia(int page, int pageSize);

}
