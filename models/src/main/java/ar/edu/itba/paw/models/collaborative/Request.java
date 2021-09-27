package ar.edu.itba.paw.models.collaborative;

public class Request {
    private final int collabId;
    private final String requesterUsername;
    private final String message;
    private final int collabType;

    public Request(int collabId, String requesterUsername, String message, int collabType) {
        this.collabId = collabId;
        this.requesterUsername = requesterUsername;
        this.message = message;
        this.collabType = collabType;
    }

    public int getCollabId() {
        return collabId;
    }

    public String getMessage() {
        return message;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }


    public int getCollabType() {
        return collabType;
    }
}
