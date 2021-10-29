package ar.edu.itba.paw.models.user;

public enum UserRole {
    USER("ROLE_USER"),
    MOD("ROLE_MOD"),
    ADMIN("ROLE_ADMIN");

    private final String roleType;

    UserRole(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }
}
