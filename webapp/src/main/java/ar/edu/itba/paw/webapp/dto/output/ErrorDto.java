package ar.edu.itba.paw.webapp.dto.output;

public class ErrorDto {

    private String message;

    public static ErrorDto fromErrorMsg(String message) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.message = message;
        return errorDto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
