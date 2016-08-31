package uk.co.mruoc.http.client;

public class Response {

    private final int statusCode;
    private final String body;
    private final Headers headers;

    private Response(ResponseBuilder builder) {
        this.statusCode = builder.statusCode;
        this.body = builder.body;
        this.headers = builder.headers;
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

    public static class ResponseBuilder {

        private int statusCode;
        private String body;
        private Headers headers;

        public ResponseBuilder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public ResponseBuilder setHeaders(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Response build() {
            return new Response(this);
        }

    }

}

