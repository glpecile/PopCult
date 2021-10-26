package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;

import java.util.List;
import java.util.Optional;

public interface StudioDao {
    Optional<Studio> getById(int studioId);

    PageContainer<Media> getMediaByStudio(Studio studio, int page, int pageSize);

}
