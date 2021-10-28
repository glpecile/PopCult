package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;

import java.util.List;
import java.util.Optional;

public interface MediaDao {

    Optional<Media> getById(int mediaId);

    List<Media> getById(List<Integer> mediaIds);

    PageContainer<Media> getMediaList(int page, int pageSize);

    PageContainer<Media> getMediaList(MediaType mediaType, int page, int pageSize);

    PageContainer<Media> getLatestMediaList(MediaType mediaType, int page, int pageSize);


}
