package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.Optional;

public interface StaffService {

    Optional<StaffMember> getById(int staffMemberId);

    PageContainer<Media> getMediaByDirector(StaffMember staffMember, int page, int pageSize);

    PageContainer<Media> getMediaByActor(StaffMember staffMember, int page, int pageSize);

    default PageContainer<Media> getMediaByRoleType(StaffMember staffMember, int page, int pageSize, RoleType roleType) {
        if (roleType == RoleType.ACTOR)
            return getMediaByActor(staffMember, page, pageSize);
        return getMediaByDirector(staffMember, page, pageSize);
    }

    PageContainer<Media> getMedia(StaffMember staffMember, int page, int pageSize);
}
