package org.assemble;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/avenger")
public class AvengersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Avenger> getAvengers() {
        return Avenger.listAll();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Avenger createAvenger(JsonObject json) {
        Avenger avenger = new Avenger();
        avenger.name = json.getString("name");
        avenger.civilName = json.getString("civilName");
        avenger.snapped = json.getBoolean("snapped");
        
        avenger.persist();

        return avenger;
    }
}
