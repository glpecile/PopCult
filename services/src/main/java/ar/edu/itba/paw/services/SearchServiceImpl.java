package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDAO;

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType) {
        return searchDAO.searchMediaByTitle(title,page,pageSize, mediaType);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title, int mediaType) {
        return searchDAO.getCountSearchMediaByTitle(title, mediaType);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize) {
        return searchDAO.searchMediaByTitle(title,page,pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Integer> getCountSearchMediaByTitle(String title) {
        return searchDAO.getCountSearchMediaByTitle(title);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, int mediaType, int sort) {
        return searchDAO.searchMediaByTitle(title,page,pageSize,mediaType,sort);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort) {
        return searchDAO.searchListMediaByName(name,page,pageSize, sort);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> searchMediaByTitle(String title, int page, int pageSize, List<Integer> mediaType, int sort, List<Integer> genre, String fromDate, String toDate) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date fDate = null;
        Date tDate = null;
        if(fromDate != null && toDate != null){
                fDate = f.parse(fromDate+ "-01-01");
                tDate = f.parse(toDate + "-12-31");
        }
        return searchDAO.searchMediaByTitle(title,page,pageSize,mediaType,sort,genre, fDate, tDate);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<MediaList> searchListMediaByName(String name, int page, int pageSize, int sort, int genre, int minMatches) {
        if(genre == Genre.ALL.ordinal() + 1)
            return searchListMediaByName(name, page, pageSize, sort);
        return searchDAO.searchListMediaByName(name,page,pageSize,sort,genre, minMatches);
    }

    @Transactional(readOnly = true)
    @Override
    public PageContainer<Media> searchMediaByTitleNotInList(int listId, String title, int page, int pageSize, int mediaType, int sort) {
        return searchDAO.searchMediaByTitleNotInList(listId, title, page, pageSize, mediaType, sort);
    }

}
