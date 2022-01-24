package ar.edu.itba.paw.webapp.dto.output;

public class ErrorDto {
    private String errorMsg;

    private String failedAttribute;

    public static ErrorDto fromErrorMsg(String errorMsg, String failedAttribute){
        ErrorDto errorDto = new ErrorDto();
        errorDto.errorMsg = errorMsg;
        errorDto.failedAttribute = failedAttribute;
        return errorDto;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFailedAttribute() {
        return failedAttribute;
    }

    public void setFailedAttribute(String failedAttribute) {
        this.failedAttribute = failedAttribute;
    }
}
