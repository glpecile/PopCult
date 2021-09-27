package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.media.Media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMediaForm {
    private List<Integer> media;

    public List<Integer> getMedia() {
        if (media == null) return new ArrayList<>();
        return media;
    }

    public void setMedia(List<Integer> media) {
        this.media = media;
    }

    public Map<Integer, String> generateMediaMap(List<Media> mediaList) {
        if (mediaList == null) return new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        for (Media m : mediaList) {
            map.put(m.getMediaId(), " " + m.getTitle() + " (" + m.getReleaseYear() + ")");
        }
        return map;
    }

}
