package org.geektimes.management;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.Map;

public interface ConfigSourceContextMBean {

    Config getConfigContext();

    ConfigSource getConfigSourceContext();

    Map<String,String> getProperties();


}
