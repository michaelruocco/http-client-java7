package uk.co.mruoc.http.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class SimpleHttpClient extends BaseHttpClient {

    private static final Logger LOG = Logger.getLogger(SimpleHttpClient.class);

    private final ResponseConverter converter = new ResponseConverter();
    private final HttpClient client;

    public SimpleHttpClient() {
        this(HttpClientBuilder.create().build());
    }

    public SimpleHttpClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public Response post(String endpoint, String entity, Headers headers) {
        HttpPost post = createPost(endpoint, entity, headers);
        return execute(post);
    }

    @Override
    public Response put(String endpoint, String entity, Headers headers) {
        HttpPut put = createPut(endpoint, entity, headers);
        return execute(put);
    }

    @Override
    public Response get(String endpoint, Headers headers) {
        HttpGet get = createGet(endpoint, headers);
        return execute(get);
    }

    @Override
    public Response delete(String endpoint, Headers headers) {
        HttpDelete delete = createDelete(endpoint, headers);
        return execute(delete);
    }

    protected Response execute(HttpRequestBase request) {
        try {
            LOG.info("performing " + request.getMethod() + " on uri " + request.getURI().toString());
            HttpResponse rawResponse = client.execute(request);
            return converter.toResponse(rawResponse);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            request.releaseConnection();
        }
    }

    private HttpPost createPost(String endpoint, String entity, Headers headers) {
        HttpPost post = new HttpPost(endpoint);
        post.setEntity(toJsonEntity(entity));
        addHeaders(post, headers);
        return post;
    }

    private HttpPut createPut(String endpoint, String entity, Headers headers) {
        HttpPut put = new HttpPut(endpoint);
        put.setEntity(toJsonEntity(entity));
        addHeaders(put, headers);
        return put;
    }

    private HttpGet createGet(String endpoint, Headers headers) {
        HttpGet get = new HttpGet(endpoint);
        headers.add(ACCEPT, APPLICATION_JSON.getMimeType());
        addHeaders(get, headers);
        return get;
    }

    private HttpDelete createDelete(String endpoint, Headers headers) {
        HttpDelete delete = new HttpDelete(endpoint);
        addHeaders(delete, headers);
        return delete;
    }

    private void addHeaders(HttpRequest request, Headers headers) {
        for (String name : headers.getNames())
            request.setHeader(name, headers.get(name));
    }

    private HttpEntity toJsonEntity(String entity) {
        LOG.info("creating entity using json " + entity);
        return new StringEntity(entity, APPLICATION_JSON);
    }

}
