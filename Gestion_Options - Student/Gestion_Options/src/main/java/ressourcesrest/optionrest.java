
package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class optionrest {

    public static OptionBusiness opB = new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String domaine) {
        List<Option> l;

        if (domaine != null) {
            l = opB.getOptionsByDomaine(domaine);
        } else {
            l = opB.getListeOptions();
        }

        if (l.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.OK).entity(l).build();
    }

    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option op = opB.getOptionByCode(code);

        if (op == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(op).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewOption(Option op) {
        if (!opB.addOption(op)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int id) {
        if (opB.deleteOption(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option op) {
        if (opB.updateOption(code, op)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
