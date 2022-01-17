package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("test")
@Component
public class TestController {

    @GET
    @Produces(value = { javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response test(){
        return Response.ok().build();
    }
}
