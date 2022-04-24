package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddCollaboratorsDto {

    @NotNull
    private List<String> collaborators;

    public List<String> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<String> collaborators) {
        this.collaborators = collaborators;
    }
}
