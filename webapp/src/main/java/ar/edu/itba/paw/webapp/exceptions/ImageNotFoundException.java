package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class ImageNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = 1314002258466016642L;

    public ImageNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.imageNotFound");
    }
}
