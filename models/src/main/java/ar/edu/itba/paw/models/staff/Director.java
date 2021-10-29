package ar.edu.itba.paw.models.staff;

import javax.persistence.*;

@Entity
@Table(name = "director")
public class Director extends Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "director_directorid_seq")
    @SequenceGenerator(sequenceName = "director_directorid_seq", name = "director_directorid_seq", allocationSize = 1)
    @Column(name = "directorid")
    private Integer directorId;

    public Director() {

    }

    public Director(Integer directorId, StaffMember staffMember) {
        super(staffMember);
        this.directorId = directorId;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }
}
