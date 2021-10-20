package ar.edu.itba.paw.models.staff;

import javax.persistence.*;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "staffMember",
                joinTable = @JoinTable(name = "crew",
                        joinColumns = @JoinColumn(name="staffmemberid"),
                        inverseJoinColumns = @JoinColumn(name = "mediaid")
                )
        )
})
@Table(name = "crew")
public class Actor extends Role {
    @Column(length = 100, nullable = false)
    private String characterName;

    public Actor(){

    }
    public Actor(StaffMember staffMember, String characterName) {
        super(staffMember);
        this.characterName = characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }
}
