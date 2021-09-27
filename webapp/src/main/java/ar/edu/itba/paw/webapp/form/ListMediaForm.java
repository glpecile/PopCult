package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.media.Media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMediaForm {
    private int[] media;

    public int[] getMedia() {
        return media;
    }

    public List<Integer> getMediaList(){
        List<Integer> mediaList = new ArrayList<>();
        for (int id: media){
            mediaList.add(id);
        }
        return mediaList;
    }

    public void setMedia(int[] media) {
        this.media = media;
    }

    public Map<Integer, String> generateMediaMap(List<Media> mediaList) {
        if (mediaList==null) return new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        for (Media m : mediaList) {
            map.put(m.getMediaId(), m.getTitle() + "(" + m.getReleaseYear() + ")");
        }
        return map;
    }

}
