package org.geektime.commons.io;

import java.io.IOException;

/**
 * 自定义Deserializer{@link Deserializer}\
 * @see java.io.Serializable
 * */
public interface Deserializer<T> {

    T deserialize(byte[] bytes)throws IOException, ClassNotFoundException;

}
