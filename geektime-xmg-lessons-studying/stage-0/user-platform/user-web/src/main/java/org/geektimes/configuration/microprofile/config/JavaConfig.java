package org.geektimes.configuration.microprofile.config;

import lombok.SneakyThrows;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.logging.UserWebLoggingConfiguration;
import org.geektimes.projects.user.domain.User;
import org.geektimes.utils.ConverterPlus;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

public class JavaConfig implements Config {

    /**
     * 内部可变的集合，不要直接暴露在外面
     */
    private List<ConfigSource> configSourceList = new LinkedList<>();

    public  String applicationName = "";


    public JavaConfig() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class,classLoader);
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        Properties applicationPro = new Properties();
        applicationPro.load(inputStream);
        applicationName = applicationPro.getProperty("applicationname");
        //lambda
        serviceLoader.forEach(configSourceList::add);
        //通过比较进行排序
        configSourceList.sort(configSourceComparator);
    }

    /**
     * 比较方法
     */
    public static Comparator<ConfigSource> configSourceComparator = new Comparator<ConfigSource>() {
        @Override
        public int compare(ConfigSource o1, ConfigSource o2) {
            return Integer.compare(o1.getOrdinal(),o2.getOrdinal());
        }
    };

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValue = getPropertyValue(propertyName);
        T result= null;
        //String转成指定类型
        try {
            result =ConverterPlus.castToT(propertyValue,propertyType,null);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName,propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return null;
    }

    protected String getPropertyValue(String propertyName){
        String propertyValue = null;
        for (ConfigSource configSource:configSourceList){
            propertyValue = configSource.getValue(propertyName);
            if (propertyValue!=null){
                break;
            }
        }
        return propertyValue;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        //
        return Collections.unmodifiableList(configSourceList);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> propertyType) {
        return Optional.empty();
    }

    @Override
    public <T> T unwrap(Class<T> propertyType) {
        return null;
    }
}