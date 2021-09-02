package ar.edu.itba.paw.models.staff;

public class StaffMember {
    private final int staffMemberId;
    private final String name;
    private final String description;
    private final String image;

    public StaffMember(int staffMemberId, String name, String description, String image) {
        this.staffMemberId = staffMemberId;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getStaffMemberId() {
        return staffMemberId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}