package pl.sagiton.bluesnapclient.service.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

public class BasicAuthInterceptor implements ClientHttpRequestInterceptor {


    private final String username;
    private final String password;

    public BasicAuthInterceptor(String username, String password ) {
        this.username = username;
        this.password = password;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution ) throws IOException {

        //Build the auth-header
        final String auth = username + ":" + password;
        final byte[] encodedAuth = Base64.getEncoder().encode( auth.getBytes( Charset.forName( "US-ASCII" ) ) );
        final String authHeader = "Basic " + new String( encodedAuth );

        //Add the auth-header
        request.getHeaders().add( "Authorization", authHeader );
        request.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json" );


        return execution.execute( request, body );
    }
}