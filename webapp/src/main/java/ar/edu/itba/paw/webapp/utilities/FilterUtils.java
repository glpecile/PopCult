package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.models.media.Genre;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class FilterUtils {

    private FilterUtils() {
        throw new AssertionError();
    }

    public static Map<String, String> getGenres(MessageSource messageSource) {
        Map<String, String> genreMap = new HashMap<>();

        for (Genre genre : Genre.values()) {
            if(genre.ordinal() != Genre.NOT.ordinal())
                genreMap.put(genre.getGenre().toUpperCase(), messageSource.getMessage(genre.getGenre(), null, LocaleContextHolder.getLocale()).toUpperCase());
        }
        return genreMap;
    }

    public static Map<String, String> getSortTypes(MessageSource messageSource) {
        Map<String, String> sortTypeMap = new HashMap<>();

        for (SortType sortType : SortType.values()) {
            sortTypeMap.put(sortType.getName().toUpperCase(), messageSource.getMessage(sortType.getName(), null, LocaleContextHolder.getLocale()));
        }

        return sortTypeMap;
    }

    public static Map<String, String> getDecades(MessageSource messageSource) {
        Map<String, String> decadeMap = new HashMap<>();

        decadeMap.put("ALL", messageSource.getMessage("AllDec", null, LocaleContextHolder.getLocale()));
        for (Integer i : IntStream.range(0, 11).map(x -> (10 * x) + 1920).toArray()) {
            String key = Integer.toString(i);
            decadeMap.put(key, messageSource.getMessage(key, null, LocaleContextHolder.getLocale()));
        }

        return decadeMap;
    }

    public static Map<String, String> getMediaTypes(MessageSource messageSource) {
        Map<String, String> mediaTypeMap = new HashMap<>();

        for(MediaType mediaType : MediaType.values()) {
            mediaTypeMap.put(mediaType.getType().toUpperCase(), messageSource.getMessage(mediaType.getType(), null, LocaleContextHolder.getLocale()));
        }

        return mediaTypeMap;
    }
}
