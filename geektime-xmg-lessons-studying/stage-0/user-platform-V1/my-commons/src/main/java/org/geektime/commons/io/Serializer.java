package org.geektime.commons.io;

import java.io.IOException;

/**
 * 自定义Serializer{@link Serializer}\
 * @see java.io.Serializable
 * */
public interface Serializer <S>{

    byte[] serialize(S source) throws IOException;

}
