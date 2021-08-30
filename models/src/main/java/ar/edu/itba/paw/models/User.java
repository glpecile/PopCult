package ar.edu.itba.paw.models;

public class User {
    private String mail;
    private String username;
    private String password;
    private String name;
    private String profilePhotoURL;

    public User(String mail, String username, String password, String name, String profilePhotoURL) {
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.name = name;
        this.profilePhotoURL = profilePhotoURL;
    }

    public User(String mail, String username, String password, String name) {
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.name = name;
        this.profilePhotoURL = "https://i.pinimg.com/736x/aa/f7/05/aaf705e06726ce3881288ae4be3ac5fe.jpg";
    }

    public String getMail() {
        return mail;
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

    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePhotoURL(String profilePhotoURL) {
        this.profilePhotoURL = profilePhotoURL;
    }
}
