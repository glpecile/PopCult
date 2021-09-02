package ar.edu.itba.paw.models.staff;

public class Person {
    private final int personId;
    private final String name;
    private final String description;
    private final String image;

    public Person(int personId, String name, String description, String image) {
        this.personId = personId;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getPersonId() {
        return personId;
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