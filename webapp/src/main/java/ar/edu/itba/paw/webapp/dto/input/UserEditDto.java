package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEditDto {

    @NotNull
    @Size(min = 3, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9\\s]+")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
