package uk.co.mruoc.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static uk.co.mruoc.http.client.Response.*;

public class ResponseConverter {

    public Response toResponse(HttpResponse apacheResponse) throws IOException {
        return new ResponseBuilder()
                .setStatusCode(extractStatusCode(apacheResponse))
                .setBody(extractBody(apacheResponse))
                .setHeaders(extractHeaders(apacheResponse))
                .build();
    }

    private static int extractStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
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

