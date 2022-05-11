package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class CommentNotFoundException extends CustomRuntimeException {
    private static final long serialVersionUID = 3387575113158365455L;

    public CommentNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.commentNotFound");
    }
}
