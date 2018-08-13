package pl.sagiton.bluesnapclient.service.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

public class BluesnapException extends HttpClientErrorException {

    public BluesnapException(HttpStatus statusCode) {
        super(statusCode);
    }

    public BluesnapException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public BluesnapException(HttpStatus statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseBody, responseCharset);
    }

    public BluesnapException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }
}
