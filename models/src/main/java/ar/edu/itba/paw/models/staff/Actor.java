package ar.edu.itba.paw.models.staff;

import javax.persistence.*;

@Entity
@Table(name = "crew")
public class Actor extends Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crew_crewid_seq")
    @SequenceGenerator(sequenceName = "crew_crewid_seq", name = "crew_crewid_seq", allocationSize = 1)
    @Column(name = "crewid")
    private Integer actorId;

    @Column(length = 100, nullable = false)
    private String characterName;

    public Actor() {

    }

    public Actor(Integer actorId, StaffMember staffMember, String characterName) {
        super(staffMember);
        this.actorId = actorId;
        this.characterName = characterName;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }
}
