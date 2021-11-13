package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MediaDao;
import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaDao mediaDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Media> getById(int mediaId) {
        return mediaDao.getById(mediaId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Media> getById(List<Integer> mediaIds) {
        return mediaDao.getById(mediaIds);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaList(MediaType mediaType, int page, int pageSize) {
        return mediaDao.getMediaList(mediaType, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getLatestMediaList(MediaType mediaType, int page, int pageSize) {
        return mediaDao.getLatestMediaList(mediaType, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> getMediaByFilters(List<MediaType> mediaType, int page, int pageSize, SortType sort, List<Genre> genre, LocalDateTime fromDate, LocalDateTime toDate, String term){
        return mediaDao.getMediaByFilters(mediaType,page,pageSize,sort,genre,fromDate,toDate, term);
    }

}
