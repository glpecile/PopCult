package ar.edu.itba.paw.models.lists;

public class ListCover {
    private int listId;
    private String name;
    private String description;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public ListCover(String name, String description, String image1, String image2, String image3, String image4) {
        this.name = name;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }
}
