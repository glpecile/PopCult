package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class CollaboratorRequestAlreadyExistsException extends CustomException {

    public CollaboratorRequestAlreadyExistsException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.collaboratorRequestAlreadyExists");
    }
}
