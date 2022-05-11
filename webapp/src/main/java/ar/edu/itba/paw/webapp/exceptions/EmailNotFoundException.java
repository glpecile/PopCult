package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class EmailNotFoundException extends CustomRuntimeException {

    public EmailNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.emailNotExists");
    }
}