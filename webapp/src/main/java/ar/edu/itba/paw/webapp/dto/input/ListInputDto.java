package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ListInputDto {

    @NotNull
    @Size(min = 3, max = 100)
    @Pattern(regexp = "[^/><]+")
    private String name;

    @NotNull
    @Size(min = 1, max = 1000)
    @Pattern(regexp = "[^></]+")
    private String description;

    @NotNull
    private boolean visible;

    @NotNull
    private boolean collaborative;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }
}
