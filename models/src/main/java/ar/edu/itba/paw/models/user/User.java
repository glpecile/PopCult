package ar.edu.itba.paw.models.user;

import ar.edu.itba.paw.models.image.Image;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    public static final int DEFAULT_IMAGE = 0;
    public static final int BAN_DAYS = 5;

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

    @Column(nullable = false)
    private boolean nonLocked;

    @Column(nullable = false)
    private int strikes;

    @Column
    private LocalDateTime banDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageid")
    private Image image;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private UserRole role;

    @Formula("(SELECT COUNT(*)\n" +
            "FROM commentnotifications n JOIN listcomment c ON n.commentid = c.commentid\n" +
            "    JOIN medialist m on c.listid = m.medialistid\n" +
            "WHERE m.userid = userid)")
    private int notifications;

    /* default */ User() {
        //Just for Hibernate, we love you!
    }

    public User(Builder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.name = builder.name;
        this.enabled = builder.enabled;
        this.nonLocked = builder.nonLocked;
        this.strikes = builder.strikes;
        this.banDate = builder.banDate;
        this.image = builder.image;
        this.role = builder.role;
        this.notifications = builder.notifications;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public int addStrike() {
        return ++this.strikes;
    }

    public LocalDateTime getBanDate() {
        return banDate;
    }

    public LocalDateTime getUnbanDate() {
        return banDate == null ? null : banDate.plusDays(BAN_DAYS);
    }

    public void setBanDate(LocalDateTime banDate) {
        this.banDate = banDate;
    }

    public Image getImage() {
        return image;
    }

    public int getImageId() {
        return image == null ? DEFAULT_IMAGE : image.getImageId();
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
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
        private boolean nonLocked = true;
        private int strikes = 0;
        private LocalDateTime banDate = null;
        private Image image = null;
        private UserRole role = UserRole.USER;
        private int notifications = 0;

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

        public Builder nonLocked(boolean nonLocked) {
            this.nonLocked = nonLocked;
            return this;
        }

        public Builder strikes(int strikes) {
            this.strikes = strikes;
            return this;
        }

        public Builder banDate(LocalDateTime banDate) {
            this.banDate = banDate;
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

        public Builder notifications(int notifications) {
            this.notifications = notifications;
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
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
