package ar.edu.itba.paw.persistence.hibernate;

import ar.edu.itba.paw.interfaces.StaffDao;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.StaffMember;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public PageContainer<Media> getMediaByDirector(int staffMemberId, int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<Media> getMediaByActor(int staffMemberId, int page, int pageSize) {
        return null;
    }

    @Override
    public PageContainer<Media> getMedia(int staffMemberId, int page, int pageSize) {
        return null;
    }

    @Override
    public List<Director> getDirectorsByMedia(int mediaId) {
        return null;
    }

    @Override
    public List<Actor> getActorsByMedia(int mediaId) {
        return null;
    }

}
