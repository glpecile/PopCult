package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.List;
import java.util.Optional;

public interface StaffService {

    Optional<StaffMember> getById(int staffMemberId);

    List<StaffMember> getPersonList();

    @Deprecated
    PageContainer<Integer> getMediaByDirectorIds(int staffMemberId, int page, int pageSize);

    PageContainer<Media> getMediaByDirector(int staffMemberId, int page, int pageSize);

    @Deprecated
    PageContainer<Integer> getMediaByActorIds(int staffMemberId, int page, int pageSize);

    PageContainer<Media> getMediaByActor(int staffMemberId, int page, int pageSize);

    default PageContainer<Integer> getMediaByRoleType(int staffMemberId, int page, int pageSize, int roleType){
        if(roleType == RoleType.ACTOR.ordinal())
            return getMediaByActorIds(staffMemberId,page,pageSize);
        return getMediaByDirectorIds(staffMemberId,page,pageSize);
    }

    @Deprecated
    PageContainer<Integer> getMediaIds(int staffMemberId, int page, int pageSize);

    PageContainer<Media> getMedia(int staffMemberId, int page, int pageSize);

    List<Director> getDirectorsByMedia(int mediaId);

    List<Actor> getActorsByMedia(int mediaId);

    Optional<Integer> getMediaCountByDirector(int staffMemberId);

    Optional<Integer> getMediaCountByActor(int staffMemberId);

    default Optional<Integer> getMediaCountByRoleType(int staffMemberId, int roleType){
        if(roleType == RoleType.ACTOR.ordinal())
            return getMediaCountByActor(staffMemberId);
        return getMediaCountByDirector(staffMemberId);
    }

    Optional<Integer> getMediaCount(int staffMemberId);

}
