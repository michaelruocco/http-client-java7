package uk.co.mruoc.http.client;

public interface HttpClient {

    Response post(String endpoint, String entity);
    Response post(String endpoint, String entity, Headers headers);

    Response put(String endpoint, String entity);
    Response put(String endpoint, String entity, Headers headers);

    Response get(String endpoint);
    Response get(String endpoint, Headers headers);

    Response delete(String endpoint);
    Response delete(String endpoint, Headers headers);

}
