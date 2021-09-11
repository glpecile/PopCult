package ar.edu.itba.paw.models.user;


public class User {
    private final int userId;
    private final String email;
    private final String userName;
    private final String password;
    private final String name;
    private final String profilePhotoURL;


    public User(int userId, String email, String userName, String password, String name, String profilePhotoURL) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.profilePhotoURL = profilePhotoURL;
    }
}
