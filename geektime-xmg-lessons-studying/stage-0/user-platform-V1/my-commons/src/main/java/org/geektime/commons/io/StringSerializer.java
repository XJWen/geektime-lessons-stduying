package org.geektime.commons.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Serializer<String>{@link Serializer}
 * */
public class StringSerializer implements Serializer<String>{
    @Override
    public byte[] serialize(String source) throws IOException {
        return source.getBytes(StandardCharsets.UTF_8);
    }
}
