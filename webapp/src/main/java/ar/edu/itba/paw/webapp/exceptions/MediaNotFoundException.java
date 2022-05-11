package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class MediaNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = 6582458591700750956L;

    public MediaNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.mediaNotFound");
    }
}
