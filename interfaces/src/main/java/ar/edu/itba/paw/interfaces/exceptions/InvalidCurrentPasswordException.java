package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class InvalidCurrentPasswordException extends CustomException {

    public InvalidCurrentPasswordException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.invalidCurrentPassword");
    }
}
