package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;

import java.util.List;

public interface SearchService {

    PageContainer<Media> searchMediaByTitleNotInList(MediaList mediaList, String title, int page, int pageSize, List<MediaType> mediaType, SortType sort);

    PageContainer<User> searchUsersToCollabNotInList(String username, MediaList mediaList, int pageSize, int page);

}
