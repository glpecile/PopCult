package ar.edu.itba.paw.models.staff;

public class StaffMember {
    private final int staffMemberId;
    private final String name;
    private final String description;
    private final String image;
    private final int role;

    public StaffMember(int staffMemberId, String name, String description, String image, int role) {
        this.staffMemberId = staffMemberId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.role = role;
    }

    public int getRole() {
        return role;
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