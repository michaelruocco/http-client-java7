package uk.co.mruoc.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Response {

    private final int statusCode;
    private final String body;
    private final Headers headers;

    public Response(int statusCode) {
        this(statusCode, "", new Headers());
    }

    public Response(int statusCode, String body) {
        this(statusCode, body, new Headers());
    }

    public Response(int statusCode, String body, Headers headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public static Response fromApacheResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        String body = extractBody(response);
        Headers headers = extractHeaders(response);
        return new Response(statusCode, body, headers);
    }

    private static String extractBody(HttpResponse response) throws IOException {
        if (response.getEntity() == null)
            return "";
        return EntityUtils.toString(response.getEntity());
    }

    private static Headers extractHeaders(HttpResponse response) {
        return new Headers(response);
    }

}

