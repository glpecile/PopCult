package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class ListAlreadyReportedException extends CustomException {

    public ListAlreadyReportedException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.listAlreadyReportedException");
    }
}
