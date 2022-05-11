package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class UserAlreadyIsModException extends CustomException {

    public UserAlreadyIsModException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.userAlreadyIsMod");
    }
}
