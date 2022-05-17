package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class InvalidQueryParamValueException extends CustomRuntimeException {
    private static final long serialVersionUID = -8814736295545707307L;

    public InvalidQueryParamValueException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.invalidQueryParamValue");
    }
}
