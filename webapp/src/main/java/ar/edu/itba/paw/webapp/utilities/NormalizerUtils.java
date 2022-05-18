package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.webapp.exceptions.InvalidQueryParamValueException;

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
        List<Genre> genreList;
        try {
            genreList = genres.stream().map(g -> g.replaceAll("\\s+", "")).map(String::toUpperCase).map(Genre::valueOf).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParamValueException();
        }
        return genreList;
    }

    public static List<MediaType> getNormalizedMediaType(List<String> mediaTypes) {
        if (mediaTypes == null)
            return Collections.emptyList();
        List<MediaType> mediaTypeList;
        try {
            mediaTypeList = mediaTypes.stream().map(String::toUpperCase).map(MediaType::valueOf).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParamValueException();
        }
        return mediaTypeList;

    }

    public static RoleType getNormalizedRoleType(String roleTypeString) {
        if (roleTypeString == null)
            return null;
        RoleType roleType;
        try {
            roleType = RoleType.valueOf(roleTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParamValueException();
        }
        return roleType;
    }

    public static LocalDateTime getStartYear(String decade) {
        LocalDateTime startYear = null;
        try {
            if (decade != null && !decade.equals("ALL")) {
                startYear = LocalDateTime.of(Integer.parseInt(decade), 1, 1, 0, 0);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParamValueException();
        }
        return startYear;
    }

    public static LocalDateTime getLastYear(String decade) {
        LocalDateTime lastYear = null;
        try {
            if (decade != null && !decade.equals("ALL")) {
                lastYear = LocalDateTime.of(Integer.parseInt(decade) + 9, 12, 31, 0, 0);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParamValueException();
        }
        return lastYear;
    }

    public static SortType getNormalizedSortType(String sortTypeString) {
        SortType sortType;
        try {
            sortType = SortType.valueOf(sortTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParamValueException();
        }
        return sortType;
    }
}
