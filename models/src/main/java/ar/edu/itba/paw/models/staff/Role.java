package ar.edu.itba.paw.models.staff;

public enum Role {
    DIRECTOR("director"),
    ACTOR("actor");
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}