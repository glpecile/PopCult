package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class TokenNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = 2480009255230889194L;

    public TokenNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.tokenNotFound");
    }
}
