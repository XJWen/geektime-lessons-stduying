package org.geektimes.utils;

import org.eclipse.microprofile.config.spi.Converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

public class ConverterPlus implements Converter {
    /**
     * Convert the given string value to a specified type. Callers <em>must not</em> pass in {@code null} for
     * {@code value}; doing so may result in a {@code NullPointerException} being thrown.
     *
     * @param value the string representation of a property value (must not be {@code null})
     * @return the converted value, or {@code null} if the value is empty
     * @throws IllegalArgumentException if the value cannot be converted to the specified type
     * @throws NullPointerException     if the given value was {@code null}
     */
    @Override
    public Object convert(String value) throws IllegalArgumentException, NullPointerException {
        return value;
    }

    public static String getAsString(final Object obj){
        if (obj==null){
            return obj.toString();
        }
        return null;
    }

    public static String getAsString(final Object object,final String defaultValue){
        String stringValue = getAsString(object);
        if (stringValue==null){
            stringValue = defaultValue;
        }
        return stringValue;
    }

    public static Number getAsNumber(final Object obj) throws ParseException {
        if (obj!=null){
            if (obj instanceof Number){
                return (Number) obj;
            }else if (obj instanceof Boolean){
                return getAsBoolean(obj)?1:0;
            }else if (obj instanceof String){
                return NumberFormat.getInstance().parse(getAsString(obj,"0"));
            }else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }

    public static <N extends Number> N getAsNumber(final Object object, final N defaultValue) throws ParseException {
        Number numberValue = getAsNumber(object);
        if (numberValue==null){
            numberValue=defaultValue;
        }
        return (N)numberValue;
    }

    public static Boolean getAsBoolean(final Object obj) {
        if (obj!=null){
            if (obj instanceof Number){
                Number number = (Number) obj;
                return (number.intValue()!=0)?Boolean.TRUE:Boolean.FALSE;
            }else if (obj instanceof Boolean){
                return (boolean)obj;
            }else if (obj instanceof String){
                return Boolean.valueOf(getAsString(obj));
            }else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }

    public static Boolean getAsBoolean(final Object object,final Boolean defaultValue) throws ParseException {
        Boolean booleanValue = getAsBoolean(object);
        if (booleanValue==null){
            booleanValue=defaultValue;
        }
        return booleanValue;
    }

    public static Byte getAsByte(final Object obj) throws ParseException {
        Number number = getAsNumber(obj);
        if (obj!=null){
            if (obj instanceof Byte){
                return (Byte) obj;
            }else {
                return number.byteValue();
            }
        }
        return null;
    }

    public static Byte getAsByte(final Object object,final Byte defaultValue) throws ParseException {
        Byte byteValue = getAsByte(object);
        if (byteValue==null){
            byteValue=defaultValue;
        }
        return byteValue;
    }

    public static Short getAsShort(final Object obj) throws ParseException {
        Number number = getAsNumber(obj);
        if (obj!=null){
            if (obj instanceof Short){
                return (Short) obj;
            }else {
                return number.shortValue();
            }
        }
        return null;
    }

    public static Short getAsShort(final Object object,final Short defaultValue) throws ParseException {
        Short shortValue = getAsShort(object);
        if (shortValue==null){
            shortValue=defaultValue;
        }
        return shortValue;
    }

    public static Integer getAsInteger(final Object obj) throws ParseException {
        Number number = getAsNumber(obj);
        if (obj!=null){
            if (obj instanceof Integer){
                return (Integer) obj;
            }else {
                return number.intValue();
            }
        }
        return null;
    }

    public static Integer getAsInteger(final Object object,final Integer defaultValue) throws ParseException {
        Integer integerValue = getAsInteger(object);
        if (integerValue ==null){
            integerValue =defaultValue;
        }
        return integerValue;
    }

    public static Long getAsLong(final Object obj) throws ParseException {
        Number number = getAsNumber(obj);
        if (obj!=null){
            if (obj instanceof Long){
                return (Long) obj;
            }else {
                return number.longValue();
            }
        }
        return null;
    }

    public static Long getAsLong(final Object object,final Long defaultValue) throws ParseException {
        Long longValue = getAsLong(object);
        if (longValue ==null){
            longValue =defaultValue;
        }
        return longValue;
    }

    public static Float getAsFloat(final Object obj) throws ParseException {
        Number number = getAsNumber(obj);
        if (obj!=null){
            if (obj instanceof Float){
                return (Float) obj;
            }else {
                return number.floatValue();
            }
        }
        return null;
    }

    public static Float getAsFloat(final Object object,final Float defaultValue) throws ParseException {
        Float floatValue = getAsFloat(object);
        if (floatValue ==null){
            floatValue =defaultValue;
        }
        return floatValue;
    }

    public static Double getAsDouble(final Object obj) throws ParseException {
        Number number = getAsNumber(obj);
        if (obj!=null){
            if (obj instanceof Double){
                return (Double) obj;
            }else {
                return number.doubleValue();
            }
        }
        return null;
    }

    public static Double getAsDouble(final Object object,final Double defaultValue) throws ParseException {
        Double doubleValue = getAsDouble(object);
        if (doubleValue ==null){
            doubleValue =defaultValue;
        }
        return doubleValue;
    }

    public static BigInteger getAsBigInteger(final Object obj) throws ParseException {
        if (obj!=null){
            if (obj instanceof BigInteger){
                return (BigInteger) obj;
            }else if (obj instanceof String){
                return new BigInteger(Objects.requireNonNull(getAsString(obj)));
            }else if (obj instanceof Number||obj instanceof Boolean){
                Number number = getAsNumber(obj);
                if (number!=null){
                    return BigInteger.valueOf(number.longValue());
                }
            }else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }

    public static <I extends BigInteger> I getAsBigInteger(final Object object,final I defaultValue) throws ParseException {
        BigInteger bigIntegerValue = getAsBigInteger(object);
        if (bigIntegerValue ==null){
            bigIntegerValue =defaultValue;
        }
        return (I)bigIntegerValue;
    }

    public static BigDecimal getAsBigDecimal(final Object obj) throws ParseException {
        if (obj != null) {
            if (obj instanceof BigDecimal) {
                return (BigDecimal) obj;
            } else if (obj instanceof String) {
                return new BigDecimal((String) obj);
            } else if (obj instanceof Number || obj instanceof Boolean) {
                Number number = getAsNumber(obj);
                if (number != null) {
                    return BigDecimal.valueOf(number.doubleValue());
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }

    public static <D extends BigDecimal> D getAsBigDecimal(final Object obj, final D defaultValue) throws ParseException {
        BigDecimal answer = getAsBigDecimal(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return (D) answer;
    }

    public static <T>T castToT(final Object obj,final Class<T> clazz,final T defaultValue) throws ParseException {
        if (obj == null)
        { throw new IllegalArgumentException("'obj' must not be null"); }
        if (clazz == null)
        { throw new IllegalArgumentException("'clz' must not be null"); }
        T result = null;
        if (defaultValue==null){
            Class<?> clz = defaultValue.getClass();
            if (Boolean.class.equals(clz) || boolean.class.equals(clz)) {
                result = (T) getAsBoolean(obj, (Boolean) defaultValue);
            } else if (Byte.class.equals(clz) || byte.class.equals(clz)) {
                result = (T) getAsByte(obj, (Byte) defaultValue);
            } else if (Short.class.equals(clz) || short.class.equals(clz)) {
                result = (T) getAsShort(obj, (Short) defaultValue);
            } else if (Integer.class.equals(clz) || int.class.equals(clz)) {
                result = (T) getAsInteger(obj, (Integer) defaultValue);
            } else if (Long.class.equals(clz) || long.class.equals(clz)) {
                result = (T) getAsLong(obj, (Long) defaultValue);
            } else if (Float.class.equals(clz) || float.class.equals(clz)) {
                result = (T) getAsFloat(obj, (Float) defaultValue);
            } else if (Double.class.equals(clz) || double.class.equals(clz)) {
                result = (T) getAsDouble(obj, (Double) defaultValue);
            } else if (String.class.equals(clz)) {
                result = (T) getAsString(obj, (String) defaultValue);
            } else if (BigInteger.class.isAssignableFrom(clz)) {
                result = (T) getAsBigInteger(obj, (BigInteger) defaultValue);
            } else if (BigDecimal.class.isAssignableFrom(clz)) {
                result = (T) getAsBigDecimal(obj, (BigDecimal) defaultValue);
            } else if (Number.class.isAssignableFrom(clz)) {
                result = (T) getAsNumber(obj, (Number) defaultValue);
            } else {
                throw new UnsupportedOperationException();
            }
        }else {
            if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
                result = (T) getAsBoolean(obj);
            } else if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
                result = (T) getAsByte(obj);
            } else if (Short.class.equals(clazz) || short.class.equals(clazz)) {
                result = (T) getAsShort(obj);
            } else if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
                result = (T) getAsInteger(obj);
            } else if (Long.class.equals(clazz) || long.class.equals(clazz)) {
                result = (T) getAsLong(obj);
            } else if (Float.class.equals(clazz) || float.class.equals(clazz)) {
                result = (T) getAsFloat(obj);
            } else if (Double.class.equals(clazz) || double.class.equals(clazz)) {
                result = (T) getAsDouble(obj);
            } else if (String.class.equals(clazz)) {
                result = (T) getAsString(obj);
            } else if (BigInteger.class.isAssignableFrom(clazz)) {
                result = (T) getAsBigInteger(obj);
            } else if (BigDecimal.class.isAssignableFrom(clazz)) {
                result = (T) getAsBigDecimal(obj);
            } else if (Number.class.isAssignableFrom(clazz)) {
                result = (T) getAsNumber(obj);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        return result;
    }
}
