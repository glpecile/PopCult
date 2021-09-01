package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Media;

import java.util.List;
import java.util.Optional;

public interface MediaService {
    Optional<Media> getById(int mediaId);

    Optional<List<Media>> getMediaList();
}
