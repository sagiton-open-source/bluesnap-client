package pl.sagiton.bluesnapclient.service.exceptions;

import org.springframework.http.HttpStatus;

public class BluesnapExceptionUtils {
    public static BluesnapException wrapException(BluesnapException e) {
        String statusCode = e.getMessage().substring(0, 3);
        HttpStatus status = HttpStatus.resolve(Integer.valueOf(statusCode));

        switch (status) {
            case BAD_REQUEST:
                throw new BluesnapBadRequestException();
            case FORBIDDEN:
                throw new BluesnapAuthorizationException();
            case NOT_FOUND:
                throw new BluesnapNotFoundException();
            default:
                throw new BluesnapBadRequestException();
        }
    }
}
