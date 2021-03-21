package org.geektimes.logging;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UserWebLoggingConfiguration {

    public static Logger logger = Logger.getLogger("org.geektimes");

    public UserWebLoggingConfiguration() throws UnsupportedEncodingException {
        System.out.println("UserWebLoggingConfiguration");
        // 通过代码的方式调整日志级别
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setEncoding("UTF-8");
        consoleHandler.setLevel(Level.INFO);
        //添加handler配置
        logger.addHandler(consoleHandler);
    }

    public static void main(String[] args) {


        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("META-INF/logging.properties")){
            LogManager logManager = LogManager.getLogManager();
            //从配置文件中读取日志配置
            logManager.readConfiguration(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE,"IO出错",IOException.class);
        }
        logger.info("Hello World");
        logger.warning("2021");

    }

}
