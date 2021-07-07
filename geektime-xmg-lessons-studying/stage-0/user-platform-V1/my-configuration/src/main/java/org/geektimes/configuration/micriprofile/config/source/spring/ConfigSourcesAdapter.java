package org.geektimes.configuration.micriprofile.config.source.spring;


import org.eclipse.microprofile.config.spi.ConfigSource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ConfigSourcesAdapter {

    public List<ConfigSource> getConfigSources(Environment environment) {
        List<ConfigSource> configSourceList = new LinkedList<>();
        if (environment instanceof ConfigurableEnvironment){
               ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
               MutablePropertySources propertySources = configurableEnvironment.getPropertySources();

               propertySources.stream()
                       .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                       .map(EnumerablePropertySource.class::cast)
                       .map(PropertySourceConfigSource::new)
                       .forEach(configSourceList::add);

        }

        return unmodifiableList(configSourceList);
    }

}
