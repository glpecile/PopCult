package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class StudioNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = -847448960194276933L;

    public StudioNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.studioNotFound");
    }
}
