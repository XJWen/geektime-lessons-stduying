package org.geektimes.management;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.geektimes.configuration.microprofile.config.JavaConfig;
import org.geektimes.configuration.microprofile.config.source.JavaSystemPropertiesConfigSource;

import java.util.Map;
import java.util.prefs.BackingStoreException;

public class ConfigSourceContext implements ConfigSourceContextMBean{

    private final ConfigSource configSource;

    private final Config configContext;

    public ConfigSourceContext() throws BackingStoreException {
        JavaSystemPropertiesConfigSource systemPropertiesConfigSource = new JavaSystemPropertiesConfigSource();
        JavaConfig javaConfig = new JavaConfig();
        this.configSource = systemPropertiesConfigSource;
        this.configContext = javaConfig;

    }

    @Override
    public Config getConfigContext() {
        return configContext;
    }

    @Override
    public ConfigSource getConfigSourceContext() {
        return configSource;
    }

    @Override
    public Map<String, String> getProperties() {
        return null;
    }
}
