package org.geektimes.configuration.demo;

import java.lang.reflect.Field;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Vector;

public class ProtectionDomainDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Field field = ClassLoader.class.getField("classes");
        field.setAccessible(true);
        // Bootstrap ClassLoader ( rt.jar in JDK ) 取不到
        Vector<Class> klasses = (Vector<Class>) field.get(classLoader);

        for (Class klass :klasses){
            ProtectionDomain protectionDomain = klass.getProtectionDomain();
            CodeSource codeSource = protectionDomain.getCodeSource();
            System.out.printf("Class[%s] in %s\n", klass.getName(), codeSource.getLocation().getPath());
        }
    }
}
