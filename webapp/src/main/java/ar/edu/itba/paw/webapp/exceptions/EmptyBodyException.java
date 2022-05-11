package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class EmptyBodyException extends CustomRuntimeException {

    public EmptyBodyException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.emptyBody");
    }
}
