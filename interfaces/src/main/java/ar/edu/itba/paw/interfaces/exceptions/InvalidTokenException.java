package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class InvalidTokenException extends CustomException{

    public InvalidTokenException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.invalidToken");
    }
}
