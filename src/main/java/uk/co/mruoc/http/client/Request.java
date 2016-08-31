package uk.co.mruoc.http.client;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Request {

    private static final Logger LOG = Logger.getLogger(Headers.class);

    private final String uri;
    private final Method method;
    private final String body;
    private final Headers headers;

    public Request(String uri, Method method, String body, Headers headers) {
        this.uri = uri;
        this.method = method;
        this.body = body;
        this.headers = headers;
    }

    public Method getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getRequestUri() {
        return uri;
    }

    public static Request fromApacheRequest(HttpRequest request) throws IOException {
        String uri = request.getRequestLine().getUri();
        Method method = Method.valueOf(request.getRequestLine().getMethod());
        String body = readEntity(method, request);
        Headers headers = new Headers(request);
        return new Request(uri, method, body, headers);
    }

    private static String readEntity(Method method, HttpRequest request) {
        try {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) request;
            return EntityUtils.toString(entityRequest.getEntity());
        } catch (ClassCastException e) {
            LOG.info(method + " request does not have a body available", e);
            return "";
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
