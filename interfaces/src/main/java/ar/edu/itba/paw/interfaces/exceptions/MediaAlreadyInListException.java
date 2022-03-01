package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class MediaAlreadyInListException extends CustomException {

    public MediaAlreadyInListException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.mediaAlreadyExistsInList");
    }
}
