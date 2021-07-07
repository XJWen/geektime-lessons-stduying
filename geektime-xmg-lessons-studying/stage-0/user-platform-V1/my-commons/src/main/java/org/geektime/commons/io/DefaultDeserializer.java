package org.geektime.commons.io;

import java.io.*;

/**
 * Default {@link Deserializer} based on Java Standard Serialization.
 *
 * @see ObjectInputStream
 * @see Serializable
 */
public class DefaultDeserializer implements Deserializer<Object>{
    @Override
    public Object deserialize(byte[] bytes) throws IOException,ClassNotFoundException {
        if (bytes ==null){
            return null;
        }
        Object value = null;
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ){
            // byte[] -> object
            value = objectInputStream.readObject();
        }
        return value;
    }
}
