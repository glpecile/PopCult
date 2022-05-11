package ar.edu.itba.paw.interfaces.exceptions;

import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class CommentAlreadyReportedException extends CustomException {

    public CommentAlreadyReportedException() {
        super(HttpStatusCodes.BAD_REQUEST, "exception.commentAlreadyReported");
    }
}
