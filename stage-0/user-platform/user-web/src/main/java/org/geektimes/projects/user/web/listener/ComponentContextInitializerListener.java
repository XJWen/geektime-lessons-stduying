package org.geektimes.projects.user.web.listener;


import lombok.SneakyThrows;
import org.geektimes.context.ComponentContext;
import org.geektimes.logging.UserWebLoggingConfiguration;
import org.geektimes.management.ConfigSourceContext;
import org.geektimes.management.ConfigSourceContextMBean;
import org.geektimes.management.UserManager;
import org.geektimes.management.UserManagerMBean;
import org.geektimes.projects.user.domain.User;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.prefs.BackingStoreException;


/**
 * Servlet生命周期监听器
 * {@link ComponentContext} 初始化器
 * 类似于ContextLoaderListener
 */
@WebListener
public class ComponentContextInitializerListener implements ServletContextListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext = sce.getServletContext();
        ComponentContext context = new ComponentContext();
        context.init(servletContext);
        servletContext.setAttribute(ComponentContext.CONTEXT_NAME,context);

        try {
            StandardMBean userStandardMBean =  new StandardMBean(new UserManager(new User()), UserManagerMBean.class);
            StandardMBean standardMBean = new StandardMBean(new ConfigSourceContext(), ConfigSourceContextMBean.class);
        } catch (NotCompliantMBeanException | BackingStoreException e) {
            UserWebLoggingConfiguration.logger.severe(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ComponentContext context = (ComponentContext) servletContext.getAttribute(ComponentContext.CONTEXT_NAME);
        ComponentContext context = ComponentContext.getInstance();
        context.destroy();
    }

    /**
     * 将下面的方法抽象到ComponentContext类中实现
     * */
   /* protected Connection getConnection() throws SQLException{
        Context  context =null;
        Connection connection = null;
        try{
            //此次可以浓缩成一个应用上下文
            context = new InitialContext();
            //先找到content.xml文件
            Context envContext = (Context) context.lookup("java:comp/env");
            //依赖查找
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/UserPlatformDB");
            connection = dataSource.getConnection();
        }catch (NamingException  e){
            //将日志打印到Tomcat上
            servletContext.log(e.getMessage(),e);
        }

        if (connection!=null){
            servletContext.log("获取JNDI数据库连接成功!");
        }

        return connection;
    }*/
}
