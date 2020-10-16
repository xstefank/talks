package io.xstefank;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/order")
@ApplicationScoped
public class LRAResource {

    private Map<String, Order> orders = new HashMap<>();

    @POST
    @Path("/create")
    public Response createOrder(Order order) {
        orders.put(UUID.randomUUID().toString(), order);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/remove")
    public Response cancelOrder(@PathParam("id") String orderId) {
        Order removal = orders.remove(orderId);
        return removal != null ? Response.ok(removal.toString()).build() : Response.status(404).build();
    }

    @GET
    public Response getAllOrders() {
        return Response.ok(orders.toString()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("id") String id) {
        Order order = orders.get(id);
        return order != null ? Response.ok(order.toString()).build() : Response.status(404).build();
    }

    private void logNicely(String s) {
        System.out.println("=================");
        System.out.println(s);
        System.out.println("=================");
    }

}
