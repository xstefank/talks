package org.acme.quarkus.sample;

import org.acme.quarkus.sample.model.Avenger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return message;
    }
    
    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public Avenger getJson() {
        return new Avenger("Iron man", "Tony Stark", false, true);
    }
}
