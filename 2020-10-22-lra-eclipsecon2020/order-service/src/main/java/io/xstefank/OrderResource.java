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
        String id = UUID.randomUUID().toString();
        orders.put(id, order);

        String shipping = processShipping(id);

        return Response.ok(order.toString() + "; " + shipping).build();
    }

    @PUT
    @Path("/remove")
    public Response cancelOrder(@HeaderParam("Order-Id") String orderId) {
        Order order = orders.remove(orderId);
        return order != null ? Response.ok(order.toString()).build() : Response.status(404).build();
    }

    @GET
    public String getAllOrders() {
        return orders.toString();
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
}
