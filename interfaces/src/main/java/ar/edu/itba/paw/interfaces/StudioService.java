package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.staff.Studio;

import java.util.List;

public interface StudioService {
    List<Studio> getStudioByMediaId(int mediaId);
    List<Studio> getStudios();
}
