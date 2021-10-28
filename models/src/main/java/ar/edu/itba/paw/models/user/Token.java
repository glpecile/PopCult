package ar.edu.itba.paw.models.user;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token")
public class Token {

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private TokenType type;

    @Id
    @Column(length = 100, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date expiryDate;

    /* default */ Token() {
        //Just for Hibernate, we love you!
    }

    public Token(User user, TokenType type, String token, Date expiryDate) {
        this.user = user;
        this.type = type;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public TokenType getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
