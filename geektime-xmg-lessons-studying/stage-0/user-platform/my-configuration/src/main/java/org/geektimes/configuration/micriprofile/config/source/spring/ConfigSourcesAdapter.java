package org.geektimes.configuration.micriprofile.config.source.spring;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class ConfigSourcesAdapter {

    public List<ConfigSource> getConfigSource(Environment environment){
        List<ConfigSource> configSourceList = new LinkedList<>();
        if (environment instanceof ConfigurableEnvironment){
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
            MutablePropertySources propertySources = configurableEnvironment.getPropertySources();

            propertySources.stream()
                    .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                    .map(EnumerablePropertySource.class::cast)
                    .map(PropertyChangeSupport::new)
                    .forEach(configSourceList::add);
        }

        return Collections.unmodifiableList(configSourceList);
    }
}
