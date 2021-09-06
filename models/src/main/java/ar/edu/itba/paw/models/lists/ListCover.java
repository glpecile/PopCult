package ar.edu.itba.paw.models.lists;

public class ListCover {
    private final int listId;
    private final String name;
    private final String description;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public ListCover(int listId, String name, String description) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.image1 = "https://cdn.discordapp.com/attachments/758850104517460008/884462244145008761/dot-transparent-png-3.png";
        this.image2 = "https://cdn.discordapp.com/attachments/758850104517460008/884462244145008761/dot-transparent-png-3.png";
        this.image3 = "https://cdn.discordapp.com/attachments/758850104517460008/884462244145008761/dot-transparent-png-3.png";
        this.image4 = "https://cdn.discordapp.com/attachments/758850104517460008/884462244145008761/dot-transparent-png-3.png";
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getListId() {
        return listId;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }
}
