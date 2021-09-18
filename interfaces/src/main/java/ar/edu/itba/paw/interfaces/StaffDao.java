package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.Actor;
import ar.edu.itba.paw.models.staff.Director;
import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.List;
import java.util.Optional;

public interface StaffDao {

    Optional<StaffMember> getById(int staffMemberId);

    List<StaffMember> getPersonList();

    @Deprecated
    PageContainer<Integer> getMediaByDirectorIds(int staffMemberId, int page, int pageSize);

    PageContainer<Media> getMediaByDirector(int staffMemberId, int page, int pageSize);
    @Deprecated
    PageContainer<Integer> getMediaByActorIds(int staffMemberId, int page, int pageSize);

    PageContainer<Media> getMediaByActor(int staffMemberId, int page, int pageSize);
    @Deprecated
    PageContainer<Integer> getMediaIds(int staffMemberId, int page, int pageSize);

    PageContainer<Media> getMedia(int staffMemberId, int page, int pageSize);

    List<Director> getDirectorsByMedia(int mediaId);

    List<Actor> getActorsByMedia(int mediaId);

    Optional<Integer> getMediaCountByDirector(int staffMemberId);

    Optional<Integer> getMediaCountByActor(int staffMemberId);

    Optional<Integer> getMediaCount(int staffMemberId);
}
