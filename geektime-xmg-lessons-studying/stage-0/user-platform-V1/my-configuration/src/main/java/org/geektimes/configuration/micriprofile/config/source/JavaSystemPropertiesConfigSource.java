package org.geektimes.configuration.micriprofile.config.source;

import java.util.Map;

/**
 * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整
 * -Dapplication.name=user-web
 *
 * @return {@link System#getProperties()}
 */
public class JavaSystemPropertiesConfigSource extends MapBasedConfigSource{

    public JavaSystemPropertiesConfigSource() {
        super("Java System Properties", 400);
    }


    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        configData.putAll(System.getProperties());
    }
}
