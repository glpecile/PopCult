package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PatchMediaInListDto {

    @NotNull
    private List<Integer> add;

    @NotNull
    private List<Integer> remove;

    public List<Integer> getAdd() {
        return add;
    }

    public void setAdd(List<Integer> add) {
        this.add = add;
    }

    public List<Integer> getRemove() {
        return remove;
    }

    public void setRemove(List<Integer> remove) {
        this.remove = remove;
    }
}
