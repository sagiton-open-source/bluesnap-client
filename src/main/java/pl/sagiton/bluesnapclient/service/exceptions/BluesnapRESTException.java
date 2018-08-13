package pl.sagiton.bluesnapclient.service.exceptions;

import org.springframework.web.client.RestClientException;

public class BluesnapRESTException extends RestClientException {

    public BluesnapRESTException(String msg) {
        super(msg);
    }

    public BluesnapRESTException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
