package ar.edu.itba.paw.webapp.dto.output;

public class ErrorValidationDto {

    private String attribute;
    private String message;

    public static ErrorValidationDto fromValidationError(String attribute, String message) {
        ErrorValidationDto errorValidationDto = new ErrorValidationDto();
        errorValidationDto.attribute = attribute;
        errorValidationDto.message = message;
        return errorValidationDto;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
