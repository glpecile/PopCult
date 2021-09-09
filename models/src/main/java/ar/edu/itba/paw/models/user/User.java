package ar.edu.itba.paw.models.user;

public class User {
    private final int userId;
    private final String mail;
    private final String username;
    private final String password;
//    private String name;
//    private String profilePhotoURL;


    public User(int userId, String mail, String username, String password) {
        this.userId = userId;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }
}
