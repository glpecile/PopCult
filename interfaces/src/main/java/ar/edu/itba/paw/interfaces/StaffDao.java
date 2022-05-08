package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.Optional;

public interface StaffDao {

    Optional<StaffMember> getById(int staffMemberId);

    PageContainer<Media> getMediaByDirector(StaffMember staffMember, int page, int pageSize);

    PageContainer<Media> getMediaByActor(StaffMember staffMember, int page, int pageSize);

    PageContainer<Media> getMedia(StaffMember staffMember, int page, int pageSize);

    PageContainer<StaffMember> getAllStaff(int page, int pageSize);
}
