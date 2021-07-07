package org.geektimes.configuration.micriprofile.config;

import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * 默认实现 {@link ConfigValue}
 * */
public class DefaultConfigValue implements ConfigValue {

    private final String name;

    private final String value;

    private final String rawValue;

    private final String sourceName;

    private final int sourceOrdinal;

    DefaultConfigValue(String name, String value, String rawValue, String sourceName, int sourceOrdinal) {
        this.name = name;
        this.value = value;
        this.rawValue = rawValue;
        this.sourceName = sourceName;
        this.sourceOrdinal = sourceOrdinal;
    }

    /**
     * The name of the property.
     *
     * @return the name of the property.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * The value of the property lookup with transformations (expanded, etc).
     *
     * @return the value of the property lookup or {@code null} if the property could not be found
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * The value of the property lookup without any transformation (expanded , etc).
     *
     * @return the raw value of the property lookup or {@code null} if the property could not be found.
     */
    @Override
    public String getRawValue() {
        return rawValue;
    }

    /**
     * The {@link ConfigSource} name that loaded the property lookup.
     *
     * @return the ConfigSource name that loaded the property lookup or {@code null} if the property could not be found
     */
    @Override
    public String getSourceName() {
        return sourceName;
    }

    /**
     * The {@link ConfigSource} ordinal that loaded the property lookup.
     *
     * @return the ConfigSource ordinal that loaded the property lookup or {@code 0} if the property could not be found
     */
    @Override
    public int getSourceOrdinal() {
        return sourceOrdinal;
    }
}
