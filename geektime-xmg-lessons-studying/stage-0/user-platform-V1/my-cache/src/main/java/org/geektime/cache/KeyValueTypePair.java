package org.geektime.cache;


import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

import static org.geektime.commons.reflect.util.TypeUtils.resolveTypeArguments;

/**
 * The type pair of key and value.
 *
 */
class KeyValueTypePair {

    private final Class<?> keyType;

    private final Class<?> valueType;

    KeyValueTypePair(Class<?> keyType, Class<?> valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }

    public static  KeyValueTypePair resolve(Class<?> targetClass){
        assertCache(targetClass);

        List<Class<?>> typeArguments = resolveTypeArguments(targetClass);

        if (typeArguments.size() == 2){
            return new KeyValueTypePair(typeArguments.get(0),typeArguments.get(1));
        }
        return null;
    }

    private static void assertCache(Class<?> cacheClass) {
        if (cacheClass.isInterface()){
            throw new IllegalArgumentException("The implementation class of Cache must not be an interface!");
        }
        if (Modifier.isAbstract(cacheClass.getModifiers())){
            throw new IllegalArgumentException("The implementation class of Cache must not be abstract!");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        KeyValueTypePair that = (KeyValueTypePair) obj;
        return Objects.equals(keyType, that.keyType) && Objects.equals(valueType, that.valueType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyType, valueType);
    }

    public Class<?> getKeyType() {
        return keyType;
    }

    public Class<?> getValueType() { return valueType; }
}