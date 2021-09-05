package ar.edu.itba.paw.models.staff;

public class Studio {
    private int studioId;
    private String name;
    private String image;

    public Studio(int studioId, String name, String image) {
        this.studioId = studioId;
        this.name = name;
        this.image = image;
    }

    public int getStudioId() {
        return studioId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
