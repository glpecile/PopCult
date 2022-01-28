package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class ImageConversionException extends CustomException {

    public ImageConversionException() {
        super(HttpStatusCodes.INTERNAL_SERVER_ERROR, "exception.imageConversion");
    }
}
