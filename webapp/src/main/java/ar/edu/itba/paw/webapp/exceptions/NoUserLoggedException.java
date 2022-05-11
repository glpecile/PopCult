package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class NoUserLoggedException extends CustomRuntimeException {
    private static final long serialVersionUID = -7478500746468537935L;

    public NoUserLoggedException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.noUserLogged");
    }
}
