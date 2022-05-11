package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class EmailAlreadyVerifiedException extends CustomException {

    public EmailAlreadyVerifiedException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.emailAlreadyVerified");
    }

}
