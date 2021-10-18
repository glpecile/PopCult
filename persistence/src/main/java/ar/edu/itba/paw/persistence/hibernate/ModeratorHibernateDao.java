package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.ModeratorDao;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.user.ModRequest;
import ar.edu.itba.paw.models.user.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.Optional;

@Primary
@Repository
public class ModeratorHibernateDao implements ModeratorDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ModeratorHibernateDao.class);

    @Override
    public PageContainer<User> getModerators(int page, int pageSize) {
        return null;
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
        return null;
    }

    @Override
    public ModRequest addModRequest(User user) throws ModRequestAlreadyExistsException {
        ModRequest modRequest = new ModRequest(null, user, new Date());
        em.persist(modRequest);
        return modRequest;
    }

    @Override
    public void removeRequest(User user) {

    }
}
