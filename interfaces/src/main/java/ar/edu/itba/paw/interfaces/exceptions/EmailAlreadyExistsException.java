package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class EmailAlreadyExistsException extends CustomException {

    public EmailAlreadyExistsException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.emailAlreadyExists");
    }
}
