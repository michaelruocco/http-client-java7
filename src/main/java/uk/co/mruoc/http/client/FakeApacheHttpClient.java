package uk.co.mruoc.http.client;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FakeApacheHttpClient implements HttpClient {

    private HttpResponse response;
    private List<Request> requests = new ArrayList<>();
    private boolean throwIo;

    @Override
    public HttpResponse execute(HttpUriRequest rawRequest) throws IOException {
        requests.add(Request.fromApacheRequest(rawRequest));
        if (throwIo)
            throw new IOException();
        return response;
    }

    public void cannedResponse(int status) {
        cannedResponse(status, "");
    }

    public void cannedResponse(int status, String body) {
        cannedResponse(status, body, new Headers());
    }

    public void cannedResponse(int status, String body, Headers headers) {
        response = makeApacheResponse(status, body, headers);
    }

    public List<Request> allRequests() {
        return requests;
    }

    public String lastRequestUri() {
        return lastRequest().getRequestUri();
    }

    public String lastRequestBody() {
        return lastRequest().getBody();
    }

    public Method lastRequestMethod() {
        return lastRequest().getMethod();
    }

    public String lastRequestHeader(String name) {
        return lastRequest().getHeader(name);
    }

    public void throwsIoException() {
        this.throwIo = true;
    }

    @Override
    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        return null;
    }

    @Override
    public HttpParams getParams() {
        return null;
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return null;
    }

     Request lastRequest() {
        return requests.get(requests.size() - 1);
    }

    private static BasicHttpResponse makeApacheResponse(int status, String body, Headers headers) {
        try {
            BasicHttpResponse apacheResponse = new BasicHttpResponse(createStatus(status));
            apacheResponse.setEntity(new StringEntity(body));
            setHeaders(apacheResponse, headers);
            return apacheResponse;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static BasicStatusLine createStatus(int statusCode) {
        return new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), statusCode, "OK");
    }

    private static void setHeaders(BasicHttpResponse apacheResponse, Headers headers) {
        for (String name : headers.getNames())
            apacheResponse.setHeader(name, headers.get(name));
    }

}
