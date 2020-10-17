package io.xstefank;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/shipping")
public class ShippingResource {

    @POST
    @Path("/create")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createShipping(String id) {
        logNicely("Processing shipment for " + id);
        return Response.ok(computeShipping(id)).build();
    }

    private void logNicely(String s) {
        System.out.println("=============");
        System.out.println(s);
        System.out.println("=============");
    }

    private String computeShipping(String id) {
        return "shipping for " + id;
    }
}
