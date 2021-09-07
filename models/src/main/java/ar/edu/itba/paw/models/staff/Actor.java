package ar.edu.itba.paw.models.staff;

public class Actor extends Role {
    private final String characterName;

    public Actor(StaffMember staffMember, String characterName) {
        super(staffMember);
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }
}
