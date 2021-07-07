package org.geektimes.configuration.micriprofile.config.servlet.listener;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class ConfigServletRequestListener implements ServletRequestListener {

    private static final ThreadLocal<Config> configThreadLocal = new ThreadLocal<>();

    /**
     * Receives notification that a ServletRequest is about to come
     * into scope of the web application.
     *
     * @param sre the ServletRequestEvent containing the ServletRequest
     *            and the ServletContext representing the web application
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        ServletContext servletContext = request.getServletContext();
        ClassLoader classLoader = servletContext.getClassLoader();
        ConfigProviderResolver configProviderResolver = ConfigProviderResolver.instance();
        Config config = configProviderResolver.getConfig(classLoader);
        configThreadLocal.set(config);
    }

    /**
     * Receives notification that a ServletRequest is about to go out
     * of scope of the web application.
     *
     * @param sre the ServletRequestEvent containing the ServletRequest
     *            and the ServletContext representing the web application
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // 防止 OOM
        configThreadLocal.remove();
    }

    public static Config getConfig() {return configThreadLocal.get();}

}
