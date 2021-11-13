package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;

import java.util.List;

public interface SearchDao {

    PageContainer<Media> searchMediaByTitleNotInList(MediaList mediaList, String title, int page, int pageSize, List<MediaType> mediaType, SortType sort);

    PageContainer<User> searchUserByUsername(String username, List<User> excludedUsers, int page, int pageSize);
}
