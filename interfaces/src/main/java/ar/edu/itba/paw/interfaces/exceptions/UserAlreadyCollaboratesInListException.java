package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class UserAlreadyCollaboratesInListException extends CustomException {

    public UserAlreadyCollaboratesInListException() {
        super(HttpStatusCodes.BAD_REQUEST, "userAlreadyCollaboratesInList");
    }
}
