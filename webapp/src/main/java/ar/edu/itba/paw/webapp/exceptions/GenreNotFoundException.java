package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class GenreNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = -7339659516152120912L;

    public GenreNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.genreNotFound");
    }
}
