package uk.co.mruoc.http.client;

public class Request {

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

}
