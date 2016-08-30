package uk.co.mruoc.http.client;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static uk.co.mruoc.http.client.Headers.*;

public class HeadersTest {

    @Test(expected = HeaderNotFoundException.class)
    public void shouldThrowExceptionIfHeaderDoesNotExist() {
        Headers headers = new Headers();

        headers.get("invalid");
    }

    @Test
    public void shouldWriteHeaderValues() {
        Headers headers = new Headers();
        headers.add("name", "value1");

        assertThat(headers.get("name")).isEqualTo("value1");
    }

    @Test
    public void shouldOverwriteExistingHeaderValues() {
        Headers headers = new Headers();
        headers.add("name", "value1");
        headers.add("name", "value2");

        assertThat(headers.get("name")).isEqualTo("value2");
    }

}
