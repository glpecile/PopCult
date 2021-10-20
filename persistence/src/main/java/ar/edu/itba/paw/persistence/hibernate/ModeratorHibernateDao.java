package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.models.user.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Primary
@Repository
public class ModeratorHibernateDao implements ModeratorDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ModeratorHibernateDao.class);

    @Override
    public PageContainer<User> getModerators(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT u.userid FROM users u WHERE u.role = :role LIMIT :limit OFFSET :offset");
        nativeQuery.setParameter("role", UserRole.MOD.ordinal());
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("offset", page * pageSize);
        @SuppressWarnings("unchecked")
        List<Long> userIds = nativeQuery.getResultList();

        final TypedQuery<User> query = em.createQuery("from User where userId in (:userIds)", User.class);
        query.setParameter("userIds", userIds);
        List<User> moderators = query.getResultList();

        final Query countQuery = em.createQuery("Select count(u.userId) from User u where role = :role");
        countQuery.setParameter("role", UserRole.MOD);
        long count = (long)countQuery.getSingleResult();

        return new PageContainer<>(moderators, page, pageSize, count);
    }

    @Override
    public void promoteToMod(int userId) {
        //TODO borrar
    }

    @Override
    public void removeMod(int userId) {
        //TODO borrar

    }

    @Override
    public PageContainer<User> getModRequesters(int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT u.userid FROM modrequests u LIMIT :limit OFFSET :offset");
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("offset", page * pageSize);
        @SuppressWarnings("unchecked")
        List<Long> userIds = nativeQuery.getResultList();

        final TypedQuery<User> query = em.createQuery("from User where userId in (:userIds)", User.class);
        query.setParameter("userIds", userIds);
        List<User> moderators = query.getResultList();

        final Query countQuery = em.createQuery("Select count(*) from ModRequest u");
        long count = (long)countQuery.getSingleResult();

        return new PageContainer<>(moderators, page, pageSize, count);
    }

    @Override
    public ModRequest addModRequest(User user) throws ModRequestAlreadyExistsException {
        ModRequest modRequest = new ModRequest(null, user, new Date());
        em.persist(modRequest);
        return modRequest;
    }

    @Override
    public void removeRequest(User user) {
        //TODO preguntar
    }
}
