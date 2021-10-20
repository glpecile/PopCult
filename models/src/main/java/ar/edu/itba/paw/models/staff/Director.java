package ar.edu.itba.paw.models.staff;

import javax.persistence.*;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "staffMember",
                joinTable = @JoinTable(name = "director",
                        joinColumns = @JoinColumn(name = "staffmemberid"),
                        inverseJoinColumns = @JoinColumn(name = "mediaid")
                )
        )
})
@Table(name = "director")
public class Director extends Role {
    public Director() {

    }

    public Director(StaffMember staffMember) {
        super(staffMember);
    }
}
