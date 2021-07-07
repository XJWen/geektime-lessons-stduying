package org.geektimes.configuration.micriprofile.config.source.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

public class ServletConfigInitializer  implements ServletContainerInitializer {

    /**
     * Notifies this <tt>ServletContainerInitializer</tt> of the startup
     * of the application represented by the given <tt>ServletContext</tt>.
     *
     * <p>If this <tt>ServletContainerInitializer</tt> is bundled in a JAR
     * file inside the <tt>WEB-INF/lib</tt> directory of an application,
     * its <tt>onStartup</tt> method will be invoked only once during the
     * startup of the bundling application. If this
     * <tt>ServletContainerInitializer</tt> is bundled inside a JAR file
     * outside of any <tt>WEB-INF/lib</tt> directory, but still
     * discoverable as described above, its <tt>onStartup</tt> method
     * will be invoked every time an application is started.
     *
     * @param c   the Set of application classes that extend, implement, or
     *            have been annotated with the class types specified by the
     *            {@link HandlesTypes HandlesTypes} annotation,
     *            or <tt>null</tt> if there are no matches, or this
     *            <tt>ServletContainerInitializer</tt> has not been annotated with
     *            <tt>HandlesTypes</tt>
     * @param servletContext the <tt>ServletContext</tt> of the web application that
     *            is being started and in which the classes contained in <tt>c</tt>
     *            were found
     * @throws ServletException if an error has occurred
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext servletContext) throws ServletException {
        // 增加 ServletContextListener
        servletContext.addListener(ServletContextConfigInitializer.class);
    }
}
