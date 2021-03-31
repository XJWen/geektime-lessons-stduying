package org.geektimes.management;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.util.Map;
import java.util.prefs.BackingStoreException;

public class ConfigSourceContext implements ConfigSourceContextMBean{

    private final ConfigSource configSource;

    private final Config configContext;

    public ConfigSourceContext(ConfigSource configSource, Config configContext) throws BackingStoreException, IOException {
        this.configSource = configSource;

        this.configContext = configContext;
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
