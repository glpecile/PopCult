package ar.edu.itba.paw.models.collaborative;

public class Request {
    private final int collabId;
    private final String requesterUsername;
    private final String title;
    private final String body;
    private final int collabType;

    public Request(int collabId, String requesterUsername, String title, String body, int collabType) {
        this.collabId = collabId;
        this.requesterUsername = requesterUsername;
        this.title = title;
        this.body = body;
        this.collabType = collabType;
    }

    public int getCollabId() {
        return collabId;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getCollabType() {
        return collabType;
    }
}
