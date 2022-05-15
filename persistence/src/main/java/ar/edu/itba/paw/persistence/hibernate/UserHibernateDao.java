package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;
import ar.edu.itba.paw.persistence.hibernate.utils.PaginationValidator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Primary
@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> getById(int userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }

    @Override
    public List<User> getById(List<Integer> userIds) {
        final TypedQuery<User> query = em.createQuery("from User where userId in :userIds", User.class);
        query.setParameter("userIds", userIds);
        return userIds.isEmpty() ? Collections.emptyList() : query.getResultList();
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
    public List<User> getByUsernames(List<String> usernames) {
        final TypedQuery<User> query = em.createQuery("from User where username in :usernames", User.class);
        query.setParameter("usernames", usernames);
        return usernames.isEmpty() ? Collections.emptyList() : query.getResultList();
    }

    @Override
    public User register(String email, String username, String password, String name) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (getByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        if (getByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
        final User user = new User.Builder(email, username, password, name).build();
        em.persist(user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    public List<User> getBannedUsers() {
        final TypedQuery<User> query = em.createQuery("from User where nonLocked = :nonLocked", User.class);
        query.setParameter("nonLocked", false);
        return query.getResultList();
    }

    @Override
    public PageContainer<User> getUsers(int page, int pageSize, UserRole userRole, Boolean banned, String term, Integer notInListId) {
        PaginationValidator.validate(page, pageSize);

        final Query nativeQuery = buildGetUsersQuery(" SELECT userid FROM (SELECT DISTINCT userid, username FROM users ", page, pageSize, userRole, banned, term, notInListId);
        @SuppressWarnings("unchecked")
        List<Long> userIds = nativeQuery.getResultList();

        final Query countQuery = buildGetUsersQuery(" SELECT COUNT(userid) FROM (SELECT DISTINCT userid, username FROM users ", null, null, userRole, banned, term, notInListId);
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<User> query = em.createQuery("FROM User WHERE userId IN :userIds ORDER BY username ASC", User.class)
                .setParameter("userIds", userIds);
        List<User> users = userIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(users, page, pageSize, count);
    }

    private Query buildGetUsersQuery(String selectFrom, Integer page, Integer pageSize, UserRole userRole, Boolean banned, String term, Integer notInListId) {
        final StringBuilder queryBuilder = new StringBuilder(selectFrom);
        final LinkedList<String> wheres = new LinkedList<>();
        final Map<String, Object> parameters = new HashMap<>();

        if (userRole != null) {
            wheres.add(" role = :role ");
            parameters.put("role", userRole.ordinal());
        }
        if (banned != null) {
            wheres.add(" nonlocked = :nonlocked ");
            parameters.put("nonlocked", !banned);
        }

        if (term != null) {
            wheres.add(" username ILIKE CONCAT('%', :username, '%') ");
            parameters.put("username", term);
        }

        if (notInListId != null) {
            wheres.add(" userid NOT IN ( " +
                    "SELECT userid FROM medialist WHERE medialistid = :listid " +
                    "UNION " +
                    "SELECT collaboratorid FROM collaborative WHERE listid = :listid AND accepted = true " +
                    ") ");
            parameters.put("listid", notInListId);
        }

        if (!wheres.isEmpty()) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(wheres.removeFirst());
            wheres.forEach(where -> queryBuilder.append(" AND ").append(where));
        }

        queryBuilder.append(" ORDER BY username ASC ");
        if (page != null && pageSize != null) {
            queryBuilder.append(" OFFSET :offset LIMIT :limit ");
            parameters.put("offset", (page - 1) * pageSize);
            parameters.put("limit", pageSize);
        }
        queryBuilder.append(" ) AS aux");

        final Query nativeQuery = em.createNativeQuery(queryBuilder.toString());
        parameters.forEach(nativeQuery::setParameter);
        return nativeQuery;
    }
}
