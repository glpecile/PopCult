package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class StaffNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = -5419059456270000317L;

    public StaffNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.staffNotFound");
    }
}
