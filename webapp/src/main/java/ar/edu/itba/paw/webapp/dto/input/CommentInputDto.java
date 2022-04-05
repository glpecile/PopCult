package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CommentInputDto {

    @Size(min = 1, max = 1000)
    @Pattern(regexp = "[^></]+")
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
