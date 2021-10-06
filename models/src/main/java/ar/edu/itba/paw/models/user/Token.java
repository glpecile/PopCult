package ar.edu.itba.paw.models.user;

import java.util.Date;

public class Token {
    private final int userId;
    private final int type;
    private final String token;
    private final Date expiryDate;

    public Token(int userId, int type, String token, Date expiryDate) {
        this.userId = userId;
        this.type = type;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
