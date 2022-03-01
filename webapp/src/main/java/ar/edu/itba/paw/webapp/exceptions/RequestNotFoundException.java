package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class RequestNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = 7229775455743941813L;

    public RequestNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.requestNotFound");
    }
}
