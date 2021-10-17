package ar.edu.itba.paw.models.user;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    public static final Integer DEFAULT_IMAGE = null;

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

    @Column()
    private Integer imageId;

    @Column(nullable = false)
    private int role;

    /* default */ User() {
        //Just for Hibernate, we love you!
    }

    public User(Integer userId, String email, String username, String password, String name, boolean enabled, Integer imageId, int role) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.imageId = imageId;
        this.role = role;
    }

    public User(Builder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.name = builder.name;
        this.enabled = builder.enabled;
        this.imageId = builder.imageId;
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

    public Integer getImageId() {
        return imageId;
    }

    public int getRole() {
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

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setRole(int role) {
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
        private Integer imageId = DEFAULT_IMAGE;
        private int role = Roles.USER.ordinal();

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

        public Builder imageId(Integer imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder role(int role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
