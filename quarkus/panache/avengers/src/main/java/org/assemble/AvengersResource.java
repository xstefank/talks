package org.assemble;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/avenger")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvengersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Avenger> getAvengers() {
        return Avenger.listAll();
    }

    @Transactional
    @POST
    public Avenger createAvenger(JsonObject json) {
        Avenger avenger = new Avenger();
        avenger.name = json.getString("name");
        avenger.civilName = json.getString("civilName");
        avenger.snapped = json.getBoolean("snapped");
        
        avenger.persist();

        return avenger;
    }

    @GET
    @Path("/{id}")
    public Avenger findById(@PathParam("id") long id) {
        return Avenger.findById(id);
    }

    @GET
    @Path("/unsnapped")
    public List<Avenger> findUnsnapped() {
        return Avenger.findUnsnapped();
    }

    @GET
    @Path("/search/{search}")
    public List<Avenger> search(@PathParam("search") String search) {
        return Avenger.list("name like :search", Parameters.with("search", "%" + search + "%"));
    }

    @GET
    @Path("/ordered")
    public List<Avenger> ordered() {
        return Avenger.list("snapped", Sort.by("real_name"), false);
    }
    
    @GET
    @Path("/space")
    public List<Avenger> findWithSpace() {
        return Avenger.<Avenger>streamAll()
            .filter(a -> a.name.contains(" "))
            .limit(1)
            .collect(Collectors.toList());
    }
}
