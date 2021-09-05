package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.staff.Studio;

import java.util.List;
import java.util.Optional;

public interface StudioDao {
    List<Studio> getStudioByMediaId(int mediaId);

    List<Integer> getMediaByStudio(int studioId, int page, int pageSize);

    Optional<Integer> getMediaCountByStudio(int studioId);

    List<Studio> getStudios();
}
