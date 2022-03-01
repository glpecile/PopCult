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
    private String changePasswordUrl;
    private String resetPasswordUrl;
    private String verificationUrl;
    private String modRequestUrl;
    private String removeModUrl;
    private String lockedUrl;
    private String favoriteMediaUrl;
    private String favoriteListsUrl;
    private String watchedMediaUrl;
    private String toWatchMediaUrl;
    private String notificationsUrl;
    private String collabRequestsUrl;
    private String recommendedMediaUrl;
    private String recommendedListsUrl;
    private String authenticateUrl;

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
        userDto.changePasswordUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("password").build().toString();
        userDto.resetPasswordUrl = url.getBaseUriBuilder().path("users").path("reset-password").build().toString();
        userDto.verificationUrl = url.getBaseUriBuilder().path("users").path("verification").build().toString();
        userDto.modRequestUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("mod-requests").build().toString();
        userDto.removeModUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("mod").build().toString();
        userDto.lockedUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("locked").build().toString();
        userDto.favoriteMediaUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("favorite-media").build().toString();
        userDto.favoriteListsUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("favorite-lists").build().toString();
        userDto.watchedMediaUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("watched-media").build().toString();
        userDto.toWatchMediaUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("to-watch-media").build().toString();
        userDto.notificationsUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("notification").build().toString();
        userDto.collabRequestsUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("collab-requests").build().toString();
        userDto.recommendedMediaUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("recommended-media").build().toString();
        userDto.recommendedListsUrl = url.getBaseUriBuilder().path("users").path(user.getUsername()).path("recommended-lists").build().toString();
        userDto.authenticateUrl = url.getBaseUriBuilder().path("authenticate").build().toString();
        return userDto;
    }

    public static List<UserDto> fromUserList(UriInfo uriInfo, List<User> userList) {
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

    public String getChangePasswordUrl() {
        return changePasswordUrl;
    }

    public void setChangePasswordUrl(String changePasswordUrl) {
        this.changePasswordUrl = changePasswordUrl;
    }

    public String getResetPasswordUrl() {
        return resetPasswordUrl;
    }

    public void setResetPasswordUrl(String resetPasswordUrl) {
        this.resetPasswordUrl = resetPasswordUrl;
    }

    public String getVerificationUrl() {
        return verificationUrl;
    }

    public void setVerificationUrl(String verificationUrl) {
        this.verificationUrl = verificationUrl;
    }

    public String getModRequestUrl() {
        return modRequestUrl;
    }

    public void setModRequestUrl(String modRequestUrl) {
        this.modRequestUrl = modRequestUrl;
    }

    public String getRemoveModUrl() {
        return removeModUrl;
    }

    public void setRemoveModUrl(String removeModUrl) {
        this.removeModUrl = removeModUrl;
    }

    public String getLockedUrl() {
        return lockedUrl;
    }

    public void setLockedUrl(String lockedUrl) {
        this.lockedUrl = lockedUrl;
    }

    public String getFavoriteMediaUrl() {
        return favoriteMediaUrl;
    }

    public void setFavoriteMediaUrl(String favoriteMediaUrl) {
        this.favoriteMediaUrl = favoriteMediaUrl;
    }

    public String getFavoriteListsUrl() {
        return favoriteListsUrl;
    }

    public void setFavoriteListsUrl(String favoriteListsUrl) {
        this.favoriteListsUrl = favoriteListsUrl;
    }

    public String getWatchedMediaUrl() {
        return watchedMediaUrl;
    }

    public void setWatchedMediaUrl(String watchedMediaUrl) {
        this.watchedMediaUrl = watchedMediaUrl;
    }

    public String getToWatchMediaUrl() {
        return toWatchMediaUrl;
    }

    public void setToWatchMediaUrl(String toWatchMediaUrl) {
        this.toWatchMediaUrl = toWatchMediaUrl;
    }

    public String getNotificationsUrl() {
        return notificationsUrl;
    }

    public void setNotificationsUrl(String notificationsUrl) {
        this.notificationsUrl = notificationsUrl;
    }

    public String getCollabRequestsUrl() {
        return collabRequestsUrl;
    }

    public void setCollabRequestsUrl(String collabRequestsUrl) {
        this.collabRequestsUrl = collabRequestsUrl;
    }

    public String getRecommendedMediaUrl() {
        return recommendedMediaUrl;
    }

    public void setRecommendedMediaUrl(String recommendedMediaUrl) {
        this.recommendedMediaUrl = recommendedMediaUrl;
    }

    public String getRecommendedListsUrl() {
        return recommendedListsUrl;
    }

    public void setRecommendedListsUrl(String recommendedListsUrl) {
        this.recommendedListsUrl = recommendedListsUrl;
    }

    public String getAuthenticateUrl() {
        return authenticateUrl;
    }

    public void setAuthenticateUrl(String authenticateUrl) {
        this.authenticateUrl = authenticateUrl;
    }
}
