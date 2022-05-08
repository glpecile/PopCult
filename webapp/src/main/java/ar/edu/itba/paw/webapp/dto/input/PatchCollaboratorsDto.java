package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PatchCollaboratorsDto {

    @NotNull
    private List<String> add;

    @NotNull
    private List<String> remove;

    public List<String> getAdd() {
        return add;
    }

    public void setAdd(List<String> add) {
        this.add = add;
    }

    public List<String> getRemove() {
        return remove;
    }

    public void setRemove(List<String> remove) {
        this.remove = remove;
    }

}
