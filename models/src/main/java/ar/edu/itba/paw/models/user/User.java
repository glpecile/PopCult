package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.image.Image;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    public static final int DEFAULT_IMAGE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
    private Integer userId;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageid")
    private Image image;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private UserRole role;

    /* default */ User() {
        //Just for Hibernate, we love you!
    }

    public User(Integer userId, String email, String username, String password, String name, boolean enabled, Image image, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.image = image;
        this.role = role;
    }

    public User(Builder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.name = builder.name;
        this.enabled = builder.enabled;
        this.image = builder.image;
        this.role =  builder.role;
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
        return image == null ? DEFAULT_IMAGE : image.getImageId();
    }

    public UserRole getRole() {
        return role;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public static class Builder {
        //Required parameters
        private final String email;
        private final String username;
        private final String password;
        private final String name;
        //Initialized to default values
        private Integer userId = null;
        private boolean enabled = false;
        private Image image = null;
        private UserRole role = UserRole.USER;

        public Builder(String email, String username, String password, String name) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.name = name;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder image(Image image) {
            this.image = image;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
