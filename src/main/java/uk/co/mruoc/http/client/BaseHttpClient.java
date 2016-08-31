package uk.co.mruoc.http.client;

public abstract class BaseHttpClient implements HttpClient {
    
    @Override
    public Response post(String endpoint, String entity) {
        return post(endpoint, entity, new Headers());
    }

    @Override
    public abstract Response post(String endpoint, String entity, Headers headers);

    @Override
    public Response put(String endpoint, String entity) {
        return put(endpoint, entity, new Headers());
    }

    @Override
    public abstract Response put(String endpoint, String entity, Headers headers);

    @Override
    public Response get(String endpoint) {
        return get(endpoint, new Headers());
    }

    @Override
    public abstract Response get(String endpoint, Headers headers);

    @Override
    public Response delete(String endpoint) {
        return delete(endpoint, new Headers());
    }

    @Override
    public abstract Response delete(String endpoint, Headers headers);
    
}
