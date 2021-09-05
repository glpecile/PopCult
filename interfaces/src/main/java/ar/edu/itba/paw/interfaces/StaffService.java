package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.List;
import java.util.Optional;

public interface StaffService {

    Optional<StaffMember> getById(int staffMemberId);

    List<StaffMember> getPersonList();

    List<Integer> getMediaByDirector(int staffMemberId, int page, int pageSize);

    List<Integer> getMediaByActor(int staffMemberId, int page, int pageSize);

    public List<Integer> getMediaByRoleType(int staffMemberId, int page, int pageSize, int roleType);

    List<Director> getDirectorsByMedia(int mediaId);

    List<Actor> getActorsByMedia(int mediaId);


}
