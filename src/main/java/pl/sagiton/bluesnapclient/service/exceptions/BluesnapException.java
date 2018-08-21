package pl.sagiton.bluesnapclient.service.exceptions;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;


public class BluesnapException extends RuntimeException {

    public BluesnapException() {
    }

    public BluesnapException(String message) {
        super(message);
    }

    public BluesnapException(String message, Throwable cause) {
        super(message, cause);
    }

    public BluesnapException(Throwable cause) {
        super(cause);
    }

    public BluesnapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BluesnapException(HttpClientErrorException httpClientErrorException) {
        super(httpClientErrorException.getStatusCode() + " " + httpClientErrorException.getMessage() + httpClientErrorException.toString());
    }

    public BluesnapException(RestClientException restClientException) {
        super(restClientException.getMessage());
    }

}
