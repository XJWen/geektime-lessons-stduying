package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class JavaSystemPropertiesConfigSource  implements ConfigSource{

    /**
     * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整
     * -Dapplication.name=user-web
     * */
    private final Map<String,String> properties;


    public JavaSystemPropertiesConfigSource() throws BackingStoreException {
       /* this.properties = new HashMap<>();
        for (String propertyName : System.getProperties().stringPropertyNames()){
            this.properties.put(propertyName,System.getProperties().getProperty(propertyName));
        }*/
        //获取系统配置
        Preferences preferences = Preferences.userRoot();
        preferences.put("my-key","hello windows");
        preferences.flush();
        preferences.get("my-key",null);

        Map systemProperties = System.getProperties();

        this.properties = new HashMap<>(systemProperties);
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "Java System Properties";
    }
}
