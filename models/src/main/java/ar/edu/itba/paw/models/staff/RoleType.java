package ar.edu.itba.paw.models.staff;

public enum RoleType {
    ACTOR("actor"),
    DIRECTOR("director");
    private final String roleType;

    RoleType(String roleType) {
        this.roleType = roleType;
    }
}
