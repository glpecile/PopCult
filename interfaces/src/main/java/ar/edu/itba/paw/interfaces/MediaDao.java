package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Media;

import java.util.List;
import java.util.Optional;

public interface MediaDao {
    Optional<Media> getById(int mediaId);

    List<Media> getMediaList();

}
