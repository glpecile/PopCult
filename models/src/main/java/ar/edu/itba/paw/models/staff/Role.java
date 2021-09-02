package ar.edu.itba.paw.models.staff;

public abstract class Role {

    private final StaffMember staffMember;

    public Role(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public StaffMember getStaffMember() {
        return staffMember;
    }
}
