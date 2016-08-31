package uk.co.mruoc.http.client;

import org.apache.http.client.methods.HttpRequestBase;

import java.util.List;

public class FakeHttpClient extends SimpleHttpClient {

    private RuntimeException exception;
    private FakeApacheHttpClient fakeHttp;

    public FakeHttpClient() {
        this(new FakeApacheHttpClient());
    }

    private FakeHttpClient(FakeApacheHttpClient fakeHttp) {
        super(fakeHttp);
        this.fakeHttp = fakeHttp;
    }

    public List<Request> allRequests() {
        return fakeHttp.allRequests();
    }

    public Method lastRequestMethod() {
        return fakeHttp.lastRequestMethod();
    }

    public String lastRequestBody() {
        return fakeHttp.lastRequestBody();
    }

    public String lastRequestHeader(String name) {
        return fakeHttp.lastRequestHeader(name);
    }


    public String lastRequestUri() {
        return fakeHttp.lastRequestUri();
    }

    public void cannedResponse(int status) {
        fakeHttp.cannedResponse(status);
    }

    public void cannedResponse(int status, String entity) {
        fakeHttp.cannedResponse(status, entity);
    }

    public void cannedResponse(int status, String entity, Headers headers) {
        fakeHttp.cannedResponse(status, entity, headers);
    }

    public void throwsException(RuntimeException exception) {
        this.exception = exception;
    }

    public void throwsIoException() {
        fakeHttp.throwsIoException();
    }

    @Override
    protected Response execute(HttpRequestBase request) {
        if (exception == null)
            return super.execute(request);
        throw exception;
    }

}
