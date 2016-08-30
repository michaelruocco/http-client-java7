package uk.co.mruoc.http.client;

import org.apache.http.HttpMessage;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Headers {

    private static final Logger LOG = Logger.getLogger(Headers.class);

    private final Map<String, String> values = new HashMap<>();

    public Headers() {
        // intentionally blank
    }

    public Headers(HttpMessage message) {
        Arrays.stream(message.getAllHeaders()).forEach(h -> add(h.getName(), h.getValue()));
    }

    public void add(String name, String value) {
        logAddHeader(name, value);
        values.put(name, value);
    }

    public String get(String name) {
        if (!headerExists(name))
            throw new HeaderNotFoundException(name);
        return values.get(name);
    }

    public boolean headerExists(String name) {
        return values.containsKey(name);
    }

    public Set<String> getNames() {
        return values.keySet();
    }

    private void logAddHeader(String name, String value) {
        if (headerExists(name)) {
            logOverwriteMessage(name, value);
        } else {
            logAddMessage(name, value);
        }
    }

    private void logOverwriteMessage(String name, String value) {
        String oldValue = get(name);
        LOG.info("header " + name + " value " + oldValue + " being replaced with " + value);
    }

    private void logAddMessage(String name, String value) {
        LOG.info("adding header " + name + " with value " + value);
    }

    public static class HeaderNotFoundException extends RuntimeException {

        public HeaderNotFoundException(String headerName) {
            super(headerName);
        }

    }

}
