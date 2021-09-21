package ar.edu.itba.paw.models.user;


public class User {
    private final int userId;
    private final String email;
    private final String username;
    private final String password;
    private final String name;
    private final boolean enabled;
    private final int imageId;


    public User(int userId, String email, String username, String password, String name, boolean enabled, int imageId) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.imageId = imageId;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getImageId() {
        return imageId;
    }
}
