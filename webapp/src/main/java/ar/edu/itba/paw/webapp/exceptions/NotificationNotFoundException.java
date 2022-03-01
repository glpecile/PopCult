package ar.edu.itba.paw.webapp.exceptions;

import ar.edu.itba.paw.interfaces.exceptions.CustomRuntimeException;
import ar.edu.itba.paw.interfaces.utils.HttpStatusCodes;

public class NotificationNotFoundException extends CustomRuntimeException {

    public NotificationNotFoundException() {
        super(HttpStatusCodes.NOT_FOUND, "exception.notificationNotFound");
    }
}
