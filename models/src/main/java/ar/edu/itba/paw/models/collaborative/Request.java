package ar.edu.itba.paw.models.collaborative;

public class Request {
    private final int collabId;
    private final int collaboratorId;
    private final String collaboratorUsername;
    private final int listId;
    private final String listname;
    private final boolean accepted;

    public Request(int collabId, int collaboratorId, String collaboratorUsername, int listId, String listname, boolean accepted) {
        this.collabId = collabId;
        this.collaboratorId = collaboratorId;
        this.collaboratorUsername = collaboratorUsername;
        this.listId = listId;
        this.listname = listname;
        this.accepted = accepted;
    }

    public int getCollabId() {
        return collabId;
    }

    public int getCollaboratorId() {
        return collaboratorId;
    }

    public String getCollaboratorUsername() {
        return collaboratorUsername;
    }

    public int getListId() {
        return listId;
    }

    public String getListname() {
        return listname;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
