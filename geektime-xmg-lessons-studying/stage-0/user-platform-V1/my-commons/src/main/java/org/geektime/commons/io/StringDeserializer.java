package org.geektime.commons.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringDeserializer implements Deserializer<String> {
    @Override
    public String deserialize(byte[] bytes) throws IOException {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
