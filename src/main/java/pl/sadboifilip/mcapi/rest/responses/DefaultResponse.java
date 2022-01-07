package pl.sadboifilip.mcapi.rest.responses;

import lombok.Getter;


public class DefaultResponse {

    @Getter
    private boolean isError;
    @Getter
    private String message;

    public DefaultResponse(boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }

    public DefaultResponse(String message) {
        this(false, message);
    }

}
