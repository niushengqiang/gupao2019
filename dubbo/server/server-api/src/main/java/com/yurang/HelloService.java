package com.yurang;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public interface HelloService {
    @GET
    @Path("/say/{content}")
    String sayHello(String content);
}
