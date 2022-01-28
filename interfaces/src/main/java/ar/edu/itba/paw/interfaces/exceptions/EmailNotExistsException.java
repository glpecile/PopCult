package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class EmailNotExistsException extends CustomException {

    public EmailNotExistsException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.emailNotExists");
    }
}
