package ar.edu.itba.paw.models.staff;

public class Studio {
    private final int studioId;
    private final String name;
    private final String image;

    public Studio(int studioId, String name, String image) {
        this.studioId = studioId;
        this.name = name;
        this.image = image.equals("") ? "https://cdn.discordapp.com/attachments/851847371851956334/884854181586944030/clipart85790.png" : image;
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
