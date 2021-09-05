package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.media.Media;

import java.util.List;
import java.util.Optional;

public interface MediaDao {

    Optional<Media> getById(int mediaId);

    List<Media> getById(List<Integer> mediaIds);

    List<Media> getMediaList();

    List<Media> getMediaList(int mediaType, int page, int pageSize);

    List<Media> getMediaListByListId(int listId, int page, int pageSize);
}
