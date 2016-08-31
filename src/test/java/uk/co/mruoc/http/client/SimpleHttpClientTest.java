package uk.co.mruoc.http.client;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SimpleHttpClientTest {

    private static final String ENDPOINT = "http://localhost:8080/endpoint";
    private static final String POST_BODY = "{ post: body, another: value }";
    private static final String RESPONSE_BODY = "{ response: body, another: value }";

    private final Headers headers = new Headers();
    private final FakeApacheHttpClient fakeHttp = new FakeApacheHttpClient();

    private final HttpClient client = new SimpleHttpClient(fakeHttp);

    @Before
    public void setUp() {
        fakeHttp.cannedResponse(200, RESPONSE_BODY);
    }

    @Test
    public void returnsResponseForPost() {
        Response response = client.post(ENDPOINT, "");

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void postRequestsCorrectEndpoint() {
        client.post(ENDPOINT, "");

        assertThat(fakeHttp.lastRequestUri()).isEqualTo(ENDPOINT);
    }

    @Test
    public void postBodyIsAddedToRequest() {
        client.post(ENDPOINT, POST_BODY);

        assertThat(fakeHttp.lastRequestBody()).isEqualTo(POST_BODY);
    }

    @Test
    public void postResponseBodyIsReturned() {
        Response response = client.post(ENDPOINT, "");

        assertThat(response.getBody()).isEqualTo(RESPONSE_BODY);
    }

    @Test
    public void postHeadersAreAddedToRequest() {
        headers.add("Some-Header", "Value");
        client.post(ENDPOINT, POST_BODY, headers);

        assertThat(fakeHttp.lastRequestHeader("Some-Header")).isEqualTo("Value");
    }

    @Test
    public void returnsResponseForPut() {
        Response response = client.put(ENDPOINT, "");

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void putRequestsCorrectEndpoint() {
        client.put(ENDPOINT, "");

        assertThat(fakeHttp.lastRequestUri()).isEqualTo(ENDPOINT);
    }

    @Test
    public void putBodyIsAddedToRequest() {
        client.put(ENDPOINT, POST_BODY);

        assertThat(fakeHttp.lastRequestBody()).isEqualTo(POST_BODY);
    }

    @Test
    public void putResponseBodyIsReturned() {
        Response response = client.put(ENDPOINT, "");

        assertThat(response.getBody()).isEqualTo(RESPONSE_BODY);
    }

    @Test
    public void putHeadersAreAddedToRequest() {
        headers.add("Some-Header", "Value");
        client.put(ENDPOINT, POST_BODY, headers);

        assertThat(fakeHttp.lastRequestHeader("Some-Header")).isEqualTo("Value");
    }

    @Test
    public void returnsResponseForGet() {
        Response response = client.get(ENDPOINT);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void getRequestsCorrectEndpoint() {
        client.get(ENDPOINT);

        assertThat(fakeHttp.lastRequestUri()).isEqualTo(ENDPOINT);
    }

    @Test
    public void getResponseBodyIsReturned() {
        Response response = client.get(ENDPOINT);

        assertThat(response.getBody()).isEqualTo(RESPONSE_BODY);
    }

    @Test
    public void getHeadersAreAddedToRequest() {
        headers.add("Some-Header", "Value");
        client.get(ENDPOINT, headers);

        assertThat(fakeHttp.lastRequestHeader("Some-Header")).isEqualTo("Value");
    }

    @Test
    public void returnsResponseForDelete() {
        Response response = client.delete(ENDPOINT);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void deleteRequestsCorrectEndpoint() {
        client.delete(ENDPOINT);

        assertThat(fakeHttp.lastRequestUri()).isEqualTo(ENDPOINT);
    }

    @Test
    public void deleteResponseBodyIsReturned() {
        Response response = client.delete(ENDPOINT);

        assertThat(response.getBody()).isEqualTo(RESPONSE_BODY);
    }

    @Test
    public void deleteHeadersAreAddedToRequest() {
        headers.add("Some-Header", "Value");
        client.delete(ENDPOINT, headers);

        assertThat(fakeHttp.lastRequestHeader("Some-Header")).isEqualTo("Value");
    }

}
