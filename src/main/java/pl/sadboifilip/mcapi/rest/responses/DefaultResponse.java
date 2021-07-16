package pl.sadboifilip.mcapi.rest.responses;

public class DefaultResponse {

    private boolean isError;
    private String message;

    public DefaultResponse(boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }

    public DefaultResponse(String message) {
        this(false, message);
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return isError;
    }

}
