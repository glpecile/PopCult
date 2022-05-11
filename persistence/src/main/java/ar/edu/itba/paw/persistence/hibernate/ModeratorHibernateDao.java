package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;
import ar.edu.itba.paw.persistence.hibernate.utils.PaginationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class ModeratorHibernateDao implements ModeratorDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ModeratorHibernateDao.class);

    @Override
    public PageContainer<User> getModerators(int page, int pageSize) {
        PaginationValidator.validate(page, pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT u.userid FROM users u WHERE u.role = :role LIMIT :limit OFFSET :offset");
        nativeQuery.setParameter("role", UserRole.MOD.ordinal());
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        @SuppressWarnings("unchecked")
        List<Long> userIds = nativeQuery.getResultList();

        final TypedQuery<User> query = em.createQuery("from User where userId in (:userIds)", User.class);
        query.setParameter("userIds", userIds);
        List<User> moderators = userIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        final Query countQuery = em.createQuery("Select count(u.userId) from User u where role = :role");
        countQuery.setParameter("role", UserRole.MOD);
        long count = (long)countQuery.getSingleResult();

        return new PageContainer<>(moderators, page, pageSize, count);
    }

    @Override
    public Optional<ModRequest> getModRequest(int modRequestId) {
        return Optional.ofNullable(em.find(ModRequest.class, modRequestId));
    }

    @Override
    public PageContainer<ModRequest> getModRequests(int page, int pageSize) {
        PaginationValidator.validate(page, pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT m.requestid FROM modrequests m ORDER BY date DESC LIMIT :limit OFFSET :offset");
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("offset", (page - 1) * pageSize);
        @SuppressWarnings("unchecked")
        List<Long> modRequestsIds = nativeQuery.getResultList();

        final TypedQuery<ModRequest> query = em.createQuery("from ModRequest where requestId in (:modRequestsIds) ORDER BY date DESC", ModRequest.class);
        query.setParameter("modRequestsIds", modRequestsIds);
        List<ModRequest> moderators = modRequestsIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        final Query countQuery = em.createQuery("Select count(*) from ModRequest u");
        long count = (long)countQuery.getSingleResult();

        return new PageContainer<>(moderators, page, pageSize, count);
    }

    @Override
    public ModRequest addModRequest(User user) throws ModRequestAlreadyExistsException {
        if(modRequestAlreadyExists(user)) {
            throw new ModRequestAlreadyExistsException();
        }
        ModRequest modRequest = new ModRequest(null, user, LocalDateTime.now());
        em.persist(modRequest);
        return modRequest;
    }

    private boolean modRequestAlreadyExists(User user) {
        return ((Number)em.createNativeQuery("SELECT COUNT(*) FROM modrequests WHERE userid = :userId")
                .setParameter("userId", user.getUserId())
                .getSingleResult()).intValue() != 0;
    }

    @Override
    public void removeModRequest(ModRequest modRequest) {
        em.remove(modRequest);
    }

    @Override
    public void removeRequest(User user) {
        em.createNativeQuery("DELETE FROM modrequests WHERE userid = :userId")
                .setParameter("userId", user.getUserId())
                .executeUpdate();
    }
}
