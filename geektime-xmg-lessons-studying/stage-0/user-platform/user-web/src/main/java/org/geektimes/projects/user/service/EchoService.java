package org.geektimes.projects.user.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public interface EchoService {

    String GROUP = "DEFAULT";

    String VERSION = "1.0.0";

    @Path("/echo/{message}")
    @GET
    String echo(@PathParam("message")String message);
}
