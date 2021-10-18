package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Primary
@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHibernateDao.class);

    @Override
    public Optional<User> getById(int userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        final TypedQuery<User> query = em.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        final TypedQuery<User> query = em.createQuery("from User where username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public User register(String email, String username, String password, String name, boolean enabled, int role) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        final User user = new User.Builder(email, username, password, name).build();
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> changePassword(int userId, String password) {
        //TODO borrar
        return Optional.empty();
    }

    @Override
    public Optional<User> confirmRegister(int userId, boolean enabled) {
        //TODO borrar
        return Optional.empty();
    }

    @Override
    public void updateUserProfileImage(int userId, int imageId) {
        //TODO borrar

    }

    @Override
    public void updateUserData(int userId, String email, String username, String name) {
        //TODO borrar

    }
}
