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

    private Request(RequestBuilder builder) {
        this.uri = builder.uri;
        this.method = builder.method;
        this.body = builder.body;
        this.headers = builder.headers;
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

    public static class RequestBuilder {

        private String uri;
        private Method method;
        private String body;
        private Headers headers;

        public RequestBuilder setUri(String uri) {
            this.uri = uri;
            return this;
        }

        public RequestBuilder setMethod(Method method) {
            this.method = method;
            return this;
        }

        public RequestBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public RequestBuilder setHeaders(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }

    public static Request fromApacheRequest(HttpRequest request) {
        return new RequestBuilder()
                .setUri(extractUri(request))
                .setMethod(extractMethod(request))
                .setBody(extractBody(request))
                .setHeaders(extractHeaders(request))
                .build();
    }

    private static String extractUri(HttpRequest request) {
        return request.getRequestLine().getUri();
    }

    private static Method extractMethod(HttpRequest request) {
        return Method.valueOf(request.getRequestLine().getMethod());
    }

    private static String extractBody(HttpRequest request) {
        try {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) request;
            return EntityUtils.toString(entityRequest.getEntity());
        } catch (ClassCastException e) {
            LOG.info("request does not have a body available", e);
            return "";
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Headers extractHeaders(HttpRequest request) {
        return new Headers(request);
    }

}
