package org.geektimes.configuration.micriprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class MapBasedConfigSource implements ConfigSource {

    private  final String name;

    private  final  int ordinal;

    private final Map<String,String> configData;

    protected MapBasedConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.configData = new HashMap<>();
    }

    /**
     * 获取配置数据的map
     *
     * @return 不可变Map 类型的配置数据
     * */
    @Override
    public final  Map<String,String> getProperties() {
        return Collections.unmodifiableMap(getConfigData());
    }

    protected Map<String,String>  getConfigData(){
        try {
            if (configData.isEmpty()){
                prepareConfigData(configData);
            }
        }catch (Throwable cause){
            throw new IllegalStateException("准备配置数据发生错误", cause);
        }

        return configData;
    }

    protected abstract void prepareConfigData(Map configData)throws Throwable;

    @Override
    public final String getName(){return name;}

    @Override
    public final int getOrdinal() {
        return ordinal;
    }

    @Override
    public Set<String> getPropertyNames(){return configData.keySet();}

    @Override
    public String getValue(String propertyName){ return getConfigData().get(propertyName);}

}
