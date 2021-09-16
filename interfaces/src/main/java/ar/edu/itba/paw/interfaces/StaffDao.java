package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.List;
import java.util.Optional;

public interface StaffDao {

    Optional<StaffMember> getById(int staffMemberId);

    List<StaffMember> getPersonList();

    PageContainer<Integer> getMediaByDirector(int staffMemberId, int page, int pageSize);

    PageContainer<Integer> getMediaByActor(int staffMemberId, int page, int pageSize);

    PageContainer<Integer> getMedia(int staffMemberId, int page, int pageSize);

    List<Director> getDirectorsByMedia(int mediaId);

    List<Actor> getActorsByMedia(int mediaId);

    Optional<Integer> getMediaCountByDirector(int staffMemberId);

    Optional<Integer> getMediaCountByActor(int staffMemberId);

    Optional<Integer> getMediaCount(int staffMemberId);
}
