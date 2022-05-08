package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.staff.RoleType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NormalizerUtils {
    private NormalizerUtils() {
        throw new AssertionError();
    }

    public static List<Genre> getNormalizedGenres(List<String> genres) {
        if (genres == null)
            return Collections.emptyList();
        return genres.stream().map(g -> g.replaceAll("\\s+", "")).map(String::toUpperCase).map(Genre::valueOf).collect(Collectors.toList());
    }

    public static List<MediaType> getNormalizedMediaType(List<String> mediaTypes) {
        if (mediaTypes == null)
            return Collections.emptyList();
        return mediaTypes.stream().map(String::toUpperCase).map(MediaType::valueOf).collect(Collectors.toList());

    }

    public static RoleType getNormalizedRoleType(String roleType){
        if(roleType == null)
            return null;
        return RoleType.valueOf(roleType.toUpperCase());
    }

    public static LocalDateTime getStartYear(String decade){
        LocalDateTime startYear = null;
        if(decade != null && !decade.equals("ALL")){
            startYear = LocalDateTime.of(Integer.parseInt(decade), 1, 1, 0, 0);
        }
        return startYear;
    }

    public static LocalDateTime getLastYear(String decade){
        LocalDateTime lastYear = null;
        if(decade != null && !decade.equals("ALL")){
            lastYear = LocalDateTime.of(Integer.parseInt(decade) + 9, 12, 31, 0, 0);
        }
        return lastYear;
    }


    public static SortType getNormalizedSortType(String sortType) {
        return SortType.valueOf(sortType.toUpperCase());
    }

}
