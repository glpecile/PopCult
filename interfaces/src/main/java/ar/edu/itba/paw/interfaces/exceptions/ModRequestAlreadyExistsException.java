package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class ModRequestAlreadyExistsException extends CustomException {

    public ModRequestAlreadyExistsException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.modRequestAlreadyExists");
    }
}
