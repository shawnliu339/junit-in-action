package com.shearan.junitinaction.chapter07;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpURLConnectionFactory implements ConnectionFactory {
    private URL url;

    public HttpURLConnectionFactory(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getData() throws IOException {
        return this.url.openConnection().getInputStream();
    }
}
