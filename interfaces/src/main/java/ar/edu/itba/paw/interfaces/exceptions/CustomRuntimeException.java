package ar.edu.itba.paw.interfaces.exceptions;

public class CustomRuntimeException extends RuntimeException {

    private final int statusCode;
    private final String messageCode;

    public CustomRuntimeException(int statusCode, String messageCode) {
        super();
        this.statusCode = statusCode;
        this.messageCode = messageCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
