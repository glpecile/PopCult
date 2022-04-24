package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddMediaDto {

    @NotNull
    private List<Integer> media;

    public List<Integer> getMedia() {
        return media;
    }

    public void setMedia(List<Integer> media) {
        this.media = media;
    }
}
