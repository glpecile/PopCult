package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class ReportNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = 5138867919987076669L;

    public ReportNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.reportNotFound");
    }
}
