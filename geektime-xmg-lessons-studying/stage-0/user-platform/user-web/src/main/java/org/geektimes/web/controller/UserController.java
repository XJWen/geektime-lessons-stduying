package org.geektimes.web.controller;

import org.apache.commons.lang.StringUtils;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public class UserController implements PageController {

    @Resource(name = "bean/UserService")
    private UserService userService;

    @Override
    @POST
    @Path("/register")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
            return "register.jsp";
        }
        if (userService.register(new User(userName,password,"",""))){
            return "index.jsp";
        }
        return  "failure.jsp";
    }

}
