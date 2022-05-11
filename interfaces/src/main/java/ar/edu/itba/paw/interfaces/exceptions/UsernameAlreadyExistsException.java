package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class UsernameAlreadyExistsException extends CustomException {

    public UsernameAlreadyExistsException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.usernameAlreadyExists");
    }
}
