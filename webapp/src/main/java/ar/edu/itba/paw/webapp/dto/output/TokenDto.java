package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.user.Token;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;

public class TokenDto {

    private String token;
    private LocalDateTime expiryDate;

    private String url;

    public static TokenDto fromToken(UriInfo url, Token token) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.token = token.getToken();
        tokenDto.expiryDate = token.getExpiryDate();

        tokenDto.url = url.getAbsolutePathBuilder().path(token.getToken()).toString();
        return tokenDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
