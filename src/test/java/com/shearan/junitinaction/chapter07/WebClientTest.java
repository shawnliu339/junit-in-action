package com.shearan.junitinaction.chapter07;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

public class WebClientTest {

    @Test
    public void testGetContentOk() throws Exception {
        MockConnectionFactory mockConnectionFactory = new MockConnectionFactory();
        MockInputStream mockStream = new MockInputStream();
        mockStream.setBuffer("It works");
        mockConnectionFactory.setData(mockStream);
        WebClient client = new WebClient();
        String result = client.getContent(mockConnectionFactory);
        assertEquals("It works", result);
        mockStream.verify();
    }
}

class TestableWebClient extends WebClient {
    private HttpURLConnection connection;
    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    @Override
    protected HttpURLConnection createHttpURLConnection(URL url) throws IOException {
        return this.connection;
    }
}

class MockHttpURLConnection extends HttpURLConnection {

    public InputStream getInputStream() {
        return new ByteArrayInputStream(
                "It works".getBytes()
        );
    }

    /**
     * Constructor for the HttpURLConnection.
     *
     * @param u the URL
     */
    protected MockHttpURLConnection(URL u) {
        super(u);
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {

    }
}

