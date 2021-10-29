package ar.edu.itba.paw.models.staff;

import javax.persistence.*;

@MappedSuperclass
public abstract class Role {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "staffmemberid")
    private StaffMember staffMember;

    public Role() {

    }

    public Role(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public void setStaffMember(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public StaffMember getStaffMember() {
        return staffMember;
    }
}
