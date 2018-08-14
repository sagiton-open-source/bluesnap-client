package pl.sagiton.bluesnapclient.service.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }


    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.debug("===========================request begin================================================");
        log.debug("URI         : {}", request.getURI());
        log.debug("Method      : {}", request.getMethod());
        HttpHeaders headersCopy = makeObfuscatedCopyOfRequestHeaders(request);
        log.debug("Headers     : {}", headersCopy);
        log.debug("Request body: {}", new String(body, "UTF-8"));
        log.debug("==========================request end================================================");
    }

    private HttpHeaders makeObfuscatedCopyOfRequestHeaders(HttpRequest request) {
        HttpHeaders headersCopy = new HttpHeaders();
        headersCopy.putAll(request.getHeaders());

        List<String> headerList = headersCopy.get(HttpHeaders.AUTHORIZATION);

        List<String> obfuscatedHeaderList = obfuscateAllHeadersInOriginalList(headerList);

        headersCopy.set(HttpHeaders.AUTHORIZATION, obfuscatedHeaderList.get(0));
        return headersCopy;
    }

    private List<String> obfuscateAllHeadersInOriginalList(List<String> originalList) {
        List<String> obfuscatedHeaderList = originalList.stream()
                .map(s -> obfuscateAuthorizationHeader(s))
                .collect(Collectors.toList());
        return obfuscatedHeaderList;
    }

    private String obfuscateAuthorizationHeader(String header) {
        Integer length = header.length();
        Integer replaceLength = length - 10;
        String lastTenCharacters = header.substring(replaceLength);
        String obfuscatedHeader = header.substring(0, replaceLength).replaceAll(".", "*") + lastTenCharacters;
        return obfuscatedHeader;
    }
}