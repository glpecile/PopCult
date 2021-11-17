package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.lists.MediaList;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.media.MediaType;
import ar.edu.itba.paw.models.search.SortType;
import ar.edu.itba.paw.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Repository
public class SearchHibernateDao implements SearchDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public PageContainer<Media> searchMediaByTitleNotInList(MediaList mediaList, String title, int page, int pageSize, List<MediaType> mediaType, SortType sort) {
        //Para paginacion
        //Pedimos el contenido paginado.
        String orderBy = " ORDER BY " + sort.getNameMedia();
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM media WHERE title ILIKE CONCAT('%', :title, '%') AND type IN (:mediaType) AND mediaid NOT IN (SELECT mediaid FROM listelement WHERE medialistid = :mediaListId)" + orderBy + " OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("title", title);
        nativeQuery.setParameter("mediaListId", mediaList.getMediaListId());
        nativeQuery.setParameter("mediaType", mediaType.stream().map(MediaType::ordinal).collect(Collectors.toList()));
        nativeQuery.setParameter("offset", page * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) FROM media WHERE title ILIKE CONCAT('%', :title, '%') AND type IN (:mediaType)  AND mediaid NOT IN (SELECT mediaid FROM listelement WHERE medialistid = :mediaListId)");
        countQuery.setParameter("title", title);
        countQuery.setParameter("mediaListId", mediaList.getMediaListId());
        countQuery.setParameter("mediaType", mediaType.stream().map(MediaType::ordinal).collect(Collectors.toList()));
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaIds)", Media.class);
        query.setParameter("mediaIds", mediaIds);
        List<Media> mediasList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediasList, page, pageSize, count);
    }

    @Override
    public PageContainer<User> searchUserByUsername(String username, List<User> excludedUsers, int page, int pageSize) {
        StringBuilder nativeString = new StringBuilder("SELECT userid FROM users WHERE users.username ILIKE CONCAT('%', :username, '%')");
        List<Integer> excludedUserIds = new ArrayList<>();
        excludedUsers.forEach( user -> excludedUserIds.add(user.getUserId()));
        if (!excludedUserIds.isEmpty()) nativeString.append(" AND users.userId NOT IN :excludedUserIds");
        nativeString.append(" OFFSET :offset LIMIT :limit");
        final Query nativeQuery = em.createNativeQuery(nativeString.toString());
        nativeQuery.setParameter("username", username);
        nativeQuery.setParameter("offset", page*pageSize);
        nativeQuery.setParameter("limit", pageSize);
        if (!excludedUserIds.isEmpty()) nativeQuery.setParameter("excludedUserIds", excludedUserIds);
        @SuppressWarnings("unchecked")
        final List<Long> userIds = nativeQuery.getResultList();
        final Query countQuery = em.createNativeQuery("SELECT COUNT(userid) FROM users WHERE users.username ILIKE CONCAT('%', :username, '%') AND users.userId NOT IN :excludedUserIds")
                .setParameter("username", username).setParameter("excludedUserIds", excludedUserIds);
        int count = countQuery.getFirstResult();

        final TypedQuery<User> query = em.createQuery("from User WHERE userId IN :userIds", User.class);
        query.setParameter("userIds", userIds);
        List<User> userLists = userIds.isEmpty() ? Collections.emptyList() : query.getResultList();
        return new PageContainer<>(userLists, page, pageSize, count);
    }
}
