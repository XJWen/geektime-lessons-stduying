package org.geektimes.web.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(asyncSupported = true,urlPatterns = "/async.servlet")
public class AsnycServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        PrintWriter writer = res.getWriter();
        //开启异步上下文
        AsyncContext asyncContext = req.getAsyncContext();

        writer.printf("[线程 : %s] AsyncContext 开启",Thread.currentThread().getName());

        // HTTP 接受 Servlet 线程，将任务提交到异步线程
        // 当 Web Client 请求到当前 Servlet 时，开始通过当前 Servlet 执行线程操作，
        // 当 AsyncContext#start(Runnable) 方法执行后，将任务提交到运行 Runnable
        // 线程上，Servlet 执行线程快速释放。
        // Servlet 执行线程类似于 Netty 中的 Boss 线程，Runnable 执行线程相当于
        // Netty 中的 Worker 线程。
        asyncContext.start(()->{
            writer.printf("[线程 : %s] AsyncContext Runnable 执行...", Thread.currentThread().getName());

        });
        // 主动调用 complete 方法，指示异步上下文执行结束
        asyncContext.complete();
    }
}
