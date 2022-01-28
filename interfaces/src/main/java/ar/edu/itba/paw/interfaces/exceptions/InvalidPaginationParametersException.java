package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class InvalidPaginationParametersException extends CustomException {

    public InvalidPaginationParametersException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.invalidPaginationParameters");
    }
}
