package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Studio;

import java.util.List;
import java.util.Optional;

public interface StudioService {
    Optional<Studio> getById(int studioId);

    List<Studio> getStudioByMediaId(int mediaId);

    PageContainer<Media> getMediaByStudio(Studio studio, int page, int pageSize);

    Optional<Integer> getMediaCountByStudio(int studioId);

    List<Studio> getStudios();
}
