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

    PageContainer<Media> getMediaByDirector(int staffMemberId, int page, int pageSize);

    PageContainer<Media> getMediaByActor(int staffMemberId, int page, int pageSize);

    default PageContainer<Media> getMediaByRoleType(int staffMemberId, int page, int pageSize, int roleType){
        if(roleType == RoleType.ACTOR.ordinal())
            return getMediaByActor(staffMemberId,page,pageSize);
        return getMediaByDirector(staffMemberId,page,pageSize);
    }

    PageContainer<Media> getMedia(int staffMemberId, int page, int pageSize);

    List<Director> getDirectorsByMedia(int mediaId);

    List<Actor> getActorsByMedia(int mediaId);

}
