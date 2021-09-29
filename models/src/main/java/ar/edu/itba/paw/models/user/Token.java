package ar.edu.itba.paw.models.user;

import java.util.Date;

public class Token {
    private final int userId;
    private final String token;
    private final int type;
    private final Date expiryDate;

    @Deprecated
    public Token(int userId, String token, Date expiryDate) {
        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;
        this.type = TokenType.VERIFICATION.ordinal();
    }

    public Token(int userId, String token, int type, Date expiryDate) {
        this.userId = userId;
        this.token = token;
        this.type = type;
        this.expiryDate = expiryDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public int getType() {
        return type;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
