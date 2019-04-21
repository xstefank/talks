package org.assemble;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import org.assemble.model.DataTable;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

    @GET
    @Path("/datatable")
    @Produces(MediaType.APPLICATION_JSON)
    public DataTable datatable(
        @QueryParam(value = "draw") int draw,
        @QueryParam(value = "start") int start,
        @QueryParam(value = "length") int length,
        @QueryParam(value = "search[value]") String searchVal) {

        DataTable result = new DataTable();
        result.setDraw(draw);

        PanacheQuery<Avenger> filteredAvengers;
         
        if (searchVal != null && !searchVal.isEmpty()) {
            filteredAvengers = Avenger.searchByName(searchVal);
        } else {
            filteredAvengers = Avenger.findAll();
        }

        int page_num = start / length;
        filteredAvengers.page(page_num, length);
        
        result.setRecordsFiltered(filteredAvengers.count());
        result.setData(filteredAvengers.list());
        result.setRecordsTotal(Avenger.count());

        return result;
    }
}
