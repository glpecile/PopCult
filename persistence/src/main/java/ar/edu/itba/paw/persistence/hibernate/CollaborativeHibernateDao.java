package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.CollaborativeListsDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.collaborative.Request;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class CollaborativeHibernateDao implements CollaborativeListsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Request makeNewRequest(MediaList mediaList, User user) {
        final Request request = new Request(user, mediaList);
        em.persist(request);
        return request;
    }

    @Override
    public PageContainer<Request> getRequestsByUserId(User user, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT collabid FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND m.userid = :userId WHERE accepted = :status OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("userId", user.getUserId());
        nativeQuery.setParameter("status", false);
        nativeQuery.setParameter("offset", page*pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> collabIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(collabid) FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND m.userid = :userId WHERE accepted = :status");
        countQuery.setParameter("userId", user.getUserId());
        countQuery.setParameter("status", false);
        long count = ((Number) countQuery.getSingleResult()).longValue();

        final TypedQuery<Request> typedQuery = em.createQuery("FROM Request WHERE collabId IN (:collabIds)", Request.class)
                .setParameter("collabIds", collabIds);
        List<Request> requestList = collabIds.isEmpty() ? new ArrayList<>() : typedQuery.getResultList();
        return new PageContainer<>(requestList, page, pageSize, count);
    }

//    @Override
//    public void acceptRequest(int collabId) {
//        em.createNativeQuery("UPDATE collaborative SET accepted = :status WHERE collabid = :collabId")
//                .setParameter("status", true)
//                .setParameter("collabId", collabId)
//                .executeUpdate();
//    }

    @Override
    public void rejectRequest(Request request) {
        em.remove(request);
    }

    @Override
    public PageContainer<Request> getListCollaborators(MediaList mediaList, int page, int pageSize) {
        final Query nativeQuery = em.createNativeQuery("SELECT collaboratorid FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND medialistid = :listId WHERE accepted = :status OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("listId", mediaList.getMediaListId());
        nativeQuery.setParameter("status", true);
        nativeQuery.setParameter("offset", page*pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> collaboratorsIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(collaboratorid) FROM (medialist m JOIN collaborative c ON m.medialistid = c.listid) JOIN users u on u.userid= c.collaboratorid AND medialistid = :listId WHERE accepted = :status");
        countQuery.setParameter("listId", mediaList.getMediaListId());
        countQuery.setParameter("status", true);
        long count = ((Number) countQuery.getSingleResult()).longValue();
        final TypedQuery<User> collaboratorsQuery = em.createQuery("FROM User WHERE userId IN :collaboratorIds", User.class)
                .setParameter("collaboratorIds", collaboratorsIds);
        final List<User> collaborators = collaboratorsIds.isEmpty()? new ArrayList<>() : collaboratorsQuery.getResultList();
        final TypedQuery<Request> typedQuery = em.createQuery("FROM Request WHERE collaborator IN (:collaborators)", Request.class)
                .setParameter("collaborators", collaborators);
        List<Request> requestList = typedQuery.getResultList();
        return new PageContainer<>(requestList, page, pageSize, count);
    }

    @Override
    public Optional<Request> getById(int collabId) {
        return Optional.ofNullable(em.find(Request.class, collabId));
    }
}
