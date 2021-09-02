package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.staff.StaffMember;

import java.util.List;
import java.util.Optional;

public interface StaffDao {
    Optional<StaffMember> getById(int personId);

    List<StaffMember> getPersonList();

    List<StaffMember> getPersonMedia(int role, int mediaId, int pageSize);
}
