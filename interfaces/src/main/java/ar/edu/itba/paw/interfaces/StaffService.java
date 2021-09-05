package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.List;
import java.util.Optional;

public interface StaffService {

    Optional<StaffMember> getById(int staffMemberId);

    List<StaffMember> getPersonList();

    List<Integer> getMediaByDirector(int staffMemberId, int page, int pageSize);

    List<Integer> getMediaByActor(int staffMemberId, int page, int pageSize);

    default List<Integer> getMediaByRoleType(int staffMemberId, int page, int pageSize, int roleType){
        if(roleType == RoleType.ACTOR.ordinal())
            return getMediaByActor(staffMemberId,page,pageSize);
        return getMediaByDirector(staffMemberId,page,pageSize);
    }

    List<Integer> getMedia(int staffMemberId, int page, int pageSize);

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
