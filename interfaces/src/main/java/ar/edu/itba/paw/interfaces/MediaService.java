package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface MediaService {

    Optional<Media> getById(int mediaId);

    List<Media> getById(List<Integer> mediaIds);

    PageContainer<Media> getMediaList(MediaType mediaType, int page, int pageSize);

    PageContainer<Media> getLatestMediaList(MediaType mediaType, int page, int pageSize);

    PageContainer<Media> getMediaByFilters(List<MediaType> mediaType, int page, int pageSize, SortType sort, List<Genre> genre, String fromDate, String toDate) throws ParseException;



}
