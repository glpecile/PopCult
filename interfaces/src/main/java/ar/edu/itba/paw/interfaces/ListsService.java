package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.lists.MediaList;

import java.util.Optional;

public interface ListsService {
    Optional<MediaList> getMediaListById(int mediaListId);

}
