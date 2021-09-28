package ar.edu.itba.paw.models.user;

public enum Roles {
    USER("ROLE_USER"),
    MOD("ROLE_MOD"),
    ADMIN("ROLE_ADMIN");

    private final String roleType;

    Roles(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }
}
