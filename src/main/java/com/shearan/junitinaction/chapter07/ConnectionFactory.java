package com.shearan.junitinaction.chapter07;

import java.io.IOException;
import java.io.InputStream;

public interface ConnectionFactory {
    InputStream getData() throws IOException;
}
