package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private String email;
    private String username;
    private String name;
    private boolean enabled;
    private boolean nonLocked;
    private int strikes;
    private LocalDateTime banDate;
    private LocalDateTime unbanDate;
    private UserRole role;

    private String url;

    private String imageUrl;

    public static UserDto fromUser(UriInfo url, User user) {
        UserDto userDto = new UserDto();
        userDto.email = user.getEmail();
        userDto.username = user.getUsername();
        userDto.name = user.getName();
        userDto.enabled = user.isEnabled();
        userDto.nonLocked = user.isNonLocked();
        userDto.strikes = user.getStrikes();
        userDto.banDate = user.getBanDate();
        userDto.unbanDate = user.getUnbanDate();
        userDto.role = user.getRole();

        userDto.url = url.getBaseUriBuilder().path("users").path(user.getUsername()).build().toString();
        userDto.imageUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("image").build().toString();
        return userDto;
    }

    public static List<UserDto> fromUserList(UriInfo uriInfo, List<User> userList){
        return userList.stream().map(u -> UserDto.fromUser(uriInfo, u)).collect(Collectors.toList());
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

    public LocalDateTime getBanDate() {
        return banDate;
    }

    public void setBanDate(LocalDateTime banDate) {
        this.banDate = banDate;
    }

    public LocalDateTime getUnbanDate() {
        return unbanDate;
    }

    public void setUnbanDate(LocalDateTime unbanDate) {
        this.unbanDate = unbanDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
