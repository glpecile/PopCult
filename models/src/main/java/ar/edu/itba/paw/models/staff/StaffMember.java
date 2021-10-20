package ar.edu.itba.paw.models.staff;

import javax.persistence.*;

public class StaffMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staffmember_staffmemberid_seq")
    @SequenceGenerator(sequenceName = "staffmember_staffmemberid_seq", name="staffmember_staffmemberid_seq", allocationSize = 1)
    private int staffMemberId;

    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100)
    private String description;
    @Column(length = 100)
    private String image;

    public StaffMember(){

    }
    public StaffMember(int staffMemberId, String name, String description, String image) {
        super();
        this.staffMemberId = staffMemberId;
        this.name = name;
        this.description = description;
        this.image = image.equals("") ? "https://cdn.discordapp.com/attachments/851847371851956334/884191657535344710/depositphotos_179490486-stock-illustration-profile-anonymous-face-icon-gray.png" :
                image;
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