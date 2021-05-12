package org.geektimes.configuration.micriprofile.config.converter;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link MyConverters} Test
 */
public class MyConvertersTest {

    private MyConverters<O> myConverters;

    @BeforeClass
    public static void prepare() {

    }

    @Before
    public void init() {
        myConverters = new MyConverters<O>();
    }

    @Test
    public void testResolveConvertedType() {
        assertEquals(Byte.class, myConverters.resolveConvertedType(new ByteConverter()));
        assertEquals(Short.class, myConverters.resolveConvertedType(new ShortConverter()));
        assertEquals(Integer.class, myConverters.resolveConvertedType(new IntegerConverter()));
        assertEquals(Long.class, myConverters.resolveConvertedType(new LongConverter()));
        assertEquals(Float.class, myConverters.resolveConvertedType(new FloatConverter()));
        assertEquals(Double.class, myConverters.resolveConvertedType(new DoubleConverter()));
        assertEquals(String.class, myConverters.resolveConvertedType(new StringConverter()));
    }
}
