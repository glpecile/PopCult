package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class InvalidDateException extends CustomRuntimeException {

    public InvalidDateException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.invalidDate");
    }
}
