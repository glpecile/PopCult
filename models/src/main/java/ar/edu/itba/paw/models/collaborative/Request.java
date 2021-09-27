package ar.edu.itba.paw.models.collaborative;

public class Request {
    private final int collabId;
    private final String requesterUsername;
    private final boolean accepted;

    public Request(int collabId, String requesterUsername, boolean accepted) {
        this.collabId = collabId;
        this.requesterUsername = requesterUsername;
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public int getCollabId() {
        return collabId;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }


}
