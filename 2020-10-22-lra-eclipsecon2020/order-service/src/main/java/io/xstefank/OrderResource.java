package io.xstefank;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/order")
@ApplicationScoped
public class OrderResource {

    private final Map<String, Order> orders = new HashMap<>();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
        logNicely("Creating order " + order);
        String id = UUID.randomUUID().toString();
        saveOrder(order, id);

        String shipping = processShipping(id);

        return Response.ok(order.toString() + "; " + shipping).build();
    }

    private void saveOrder(Order order, String id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        orders.put(id, order);
    }

    @PUT
    @Path("/remove")
    public Response cancelOrder(@HeaderParam("Order-Id") String orderId) {
        logNicely("Cancelling order " + orderId);
        Order order = orders.remove(orderId);
        return order != null ? Response.ok(order.toString()).build() : Response.status(404).build();
    }

    @PUT
    @Path("/confirm")
    public Response confirmOrder(@HeaderParam("Order-Id") String orderId) {
        logNicely("Confirming order " + orderId);
        Order order = orders.get(orderId);
        return order != null ? Response.ok(order.toString()).build() : Response.status(404).build();
    }

    private String processShipping(String id) {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client.target("http://localhost:8082/shipping/create")
                .request()
                .post(Entity.text(id));

            return response.getStatus() == 200 ? response.readEntity(String.class) : "error getting shipment " + response.getStatus();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @GET
    public String getAllOrders() {
        return orders.toString();
    }

    private void logNicely(String s) {
        System.out.println("===========");
        System.out.println(s);
        System.out.println("===========");
    }
}
