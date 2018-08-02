package com.shearan.junitinaction.chapter07;

import com.shearan.junitinaction.chapter07.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;

public class MockConnectionFactory implements ConnectionFactory {
    private InputStream inputStream;

    public void setData(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public InputStream getData() throws IOException {
        return this.inputStream;
    }
}
