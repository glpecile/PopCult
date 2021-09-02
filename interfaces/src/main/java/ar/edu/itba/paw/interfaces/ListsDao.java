package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.lists.MediaList;

import java.util.Optional;

public interface ListsDao {
    Optional<MediaList> getMediaListById(int mediaListId);
}
