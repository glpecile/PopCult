package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ListForm {
    @Size(min = 3, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9&$\\-.,;()\\[\\]|/\\s]")
    private String listTitle;

    @Pattern(regexp = "[a-zA-Z0-9&$\\-.,;()\\[\\]|/\\s]")
    private String description;

    private boolean visible;

    private boolean collaborative;

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
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
