package ar.edu.itba.paw.models.staff;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "staffmemberid")
    private StaffMember staffMember;

    public Role() {

    }

    public Role(StaffMember staffMember) {
        super();
        this.staffMember = staffMember;
    }

    public void setStaffMember(StaffMember staffMember) {
        this.staffMember = staffMember;
    }

    public StaffMember getStaffMember() {
        return staffMember;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
