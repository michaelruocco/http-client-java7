package uk.co.mruoc.http.client;

public class BaseHttpClient implements HttpClient {
    
    @Override
    public Response post(String endpoint, String entity) {
        return post(endpoint, entity, new Headers());
    }

    @Override
    public Response post(String endpoint, String entity, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response put(String endpoint, String entity) {
        return put(endpoint, entity, new Headers());
    }

    @Override
    public Response put(String endpoint, String entity, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response get(String endpoint) {
        return get(endpoint, new Headers());
    }

    @Override
    public Response get(String endpoint, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response delete(String endpoint) {
        return delete(endpoint, new Headers());
    }

    @Override
    public Response delete(String endpoint, Headers headers) {
        throw new UnsupportedOperationException();
    }
    
}
