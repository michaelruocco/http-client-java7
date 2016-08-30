package uk.co.mruoc.http.client.test;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.util.EntityUtils;
import uk.co.mruoc.http.client.Headers;
import uk.co.mruoc.http.client.MissingBodyException;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Request {

    private final HttpRequest innerRequest;

    public Request(HttpRequest innerRequest) {
        this.innerRequest = innerRequest;
    }

    public Method getMethod() {
        return Method.valueOf(innerRequest.getRequestLine().getMethod());
    }

    public String getBody() throws IOException {
        return readEntity();
    }

    public String getHeader(String key) {
        return getHeaders().get(key);
    }

    public Headers getHeaders() {
        return new Headers(innerRequest);
    }

    public String getRequestUri() {
        return innerRequest.getRequestLine().getUri();
    }

    private String readEntity() {
        try {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) innerRequest;
            return EntityUtils.toString(entityRequest.getEntity());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassCastException e) {
            throw new MissingBodyException(getMethod() + " request does not have a body available", e);
        }
    }

}
