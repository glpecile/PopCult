package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class UserNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = -4439804381464928244L;

    public UserNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.userNotFound");
    }
}
