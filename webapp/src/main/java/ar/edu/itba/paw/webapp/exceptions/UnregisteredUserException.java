package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnregisteredUserException extends AuthenticationException {
    private static final long serialVersionUID = -5260306874538385788L;

    public UnregisteredUserException(String msg, Throwable t) {
        super(msg, t);
    }

    public UnregisteredUserException(String msg) {
        super(msg);
    }

    public UnregisteredUserException() {
        super("User is not registered");
    }
}
