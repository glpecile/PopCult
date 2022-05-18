package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.TokenDao;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.models.user.TokenType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Primary
@Repository
public class TokenHibernateDao implements TokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Token createToken(User user, TokenType type, String token, LocalDateTime expiryDate) {
        final Token tkn = new Token(user, type, token, expiryDate);
        em.persist(tkn);
        return tkn;
    }

    @Override
    public Optional<Token> getToken(String token) {
        final TypedQuery<Token> query = em.createQuery("from Token where token = :token", Token.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public void deleteToken(Token token) {
        em.remove(token);
    }

}
