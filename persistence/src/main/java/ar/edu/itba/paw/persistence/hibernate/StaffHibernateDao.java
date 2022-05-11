package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.StaffMember;
import ar.edu.itba.paw.persistence.hibernate.utils.PaginationValidator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class StaffHibernateDao implements StaffDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<StaffMember> getById(int staffMemberId) {
        return Optional.ofNullable(em.find(StaffMember.class, staffMemberId));
    }

    @Override
    public PageContainer<Media> getMediaByDirector(StaffMember staffMember, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        //Para paginacion
        //Pedimos el contenido paginado.
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM media NATURAL JOIN director WHERE staffmemberid = :staffMemberId OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("staffMemberId", staffMember.getStaffMemberId());
        nativeQuery.setParameter("offset", (page-1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) FROM media NATURAL JOIN director WHERE staffmemberid = :staffMemberId");
        countQuery.setParameter("staffMemberId", staffMember.getStaffMemberId());
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaIds)", Media.class);
        query.setParameter("mediaIds", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    @Override
    public PageContainer<Media> getMediaByActor(StaffMember staffMember, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        //Para paginacion
        //Pedimos el contenido paginado.
        final Query nativeQuery = em.createNativeQuery("SELECT mediaid FROM media NATURAL JOIN crew WHERE staffmemberid = :staffMemberId OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("staffMemberId", staffMember.getStaffMemberId());
        nativeQuery.setParameter("offset", (page-1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createNativeQuery("SELECT COUNT(mediaid) FROM media NATURAL JOIN crew WHERE staffmemberid = :staffMemberId");
        countQuery.setParameter("staffMemberId", staffMember.getStaffMemberId());
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaIds)", Media.class);
        query.setParameter("mediaIds", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    @Override
    public PageContainer<Media> getMedia(StaffMember staffMember, int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);
        //Para paginacion
        //Pedimos el contenido paginado.
        final Query nativeQuery = em.createNativeQuery("SELECT DISTINCT(mediaid) FROM " +
                "((SELECT mediaid FROM media NATURAL JOIN director WHERE staffmemberid = :staffMemberId)" +
                "UNION" +
                "(SELECT mediaid FROM media NATURAL JOIN crew WHERE staffmemberid = :staffMemberId)) as u " +
                "OFFSET :offset LIMIT :limit");
        nativeQuery.setParameter("staffMemberId", staffMember.getStaffMemberId());
        nativeQuery.setParameter("offset", (page-1) * pageSize);
        nativeQuery.setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> mediaIds = nativeQuery.getResultList();
        //Obtenemos la cantidad total de elementos.
        final Query countQuery = em.createNativeQuery("SELECT COUNT(DISTINCT (mediaid)) FROM " +
                "((SELECT mediaid FROM media NATURAL JOIN director WHERE staffmemberid = :staffMemberId)" +
                "UNION" +
                "(SELECT mediaid FROM media NATURAL JOIN crew WHERE staffmemberid = :staffMemberId)) as u");
        countQuery.setParameter("staffMemberId", staffMember.getStaffMemberId());
        final long count = ((Number) countQuery.getSingleResult()).longValue();

        //Query que se pide con los ids ya paginados
        final TypedQuery<Media> query = em.createQuery("from Media where mediaId in (:mediaIds)", Media.class);
        query.setParameter("mediaIds", mediaIds);
        List<Media> mediaList = mediaIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(mediaList, page, pageSize, count);
    }

    @Override
    public PageContainer<StaffMember> getAllStaff(int page, int pageSize) {
        PaginationValidator.validate(page,pageSize);

        final Query nativeQuery = em.createNativeQuery("SELECT staffmemberid FROM staffmember OFFSET :offset LIMIT :limit")
                .setParameter("offset",(page-1) * pageSize)
                .setParameter("limit", pageSize);
        @SuppressWarnings("unchecked")
        List<Long> staffIds = nativeQuery.getResultList();

        final Query countQuery = em.createQuery("SELECT COUNT(*) FROM StaffMember");
        final long count = (long) countQuery.getSingleResult();

        final TypedQuery<StaffMember> query = em.createQuery("FROM StaffMember WHERE staffmemberid IN :staffMemberIds", StaffMember.class)
                .setParameter("staffMemberIds", staffIds);
        List<StaffMember> staffMembers = staffIds.isEmpty() ? Collections.emptyList() : query.getResultList();

        return new PageContainer<>(staffMembers,page,pageSize,count);

    }

}
