package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class ListNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = -2204565509966334108L;

    public ListNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.listNotFound");
    }
}
