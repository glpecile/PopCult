package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SearchDAO;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType) {
        return searchDAO.searchMediaByTitle(title,page,pageSize, mediaType);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType) {
        return searchDAO.getCountSearchMediaByTitle(title, mediaType);
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize) {
        return searchDAO.searchMediaByTitle(title,page,pageSize);
    }

    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title) {
        return searchDAO.getCountSearchMediaByTitle(title);
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort) {
        return searchDAO.searchMediaByTitle(title,page,pageSize,mediaType,sort);
    }

    @Override
    public PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort) {
        return searchDAO.searchListMediaByName(name,page,pageSize, sort);
    }

    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, List<Integer> mediaType, int sort, List<Integer> genre, Date fromDate, Date toDate) {
        return searchDAO.searchMediaByTitle(title,page,pageSize,mediaType,sort,genre, fromDate, toDate);
    }

    @Override
    public PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort, int genre, int minMatches) {
        if(genre == Genre.ALL.ordinal() + 1)
            return searchListMediaByName(name, page, pageSize, sort);
        return searchDAO.searchListMediaByName(name,page,pageSize,sort,genre, minMatches);
    }

    @Override
    public PageContainer<Media> searchMediaByTitleNotInList(int listId, String title, int page, int pageSize, int mediaType, int sort) {
        return searchDAO.searchMediaByTitleNotInList(listId, title, page, pageSize, mediaType, sort);
    }

}
