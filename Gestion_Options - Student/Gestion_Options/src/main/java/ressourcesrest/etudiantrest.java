
package ressourcesrest;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
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

@Path("etudiants")
public class etudiantrest {

    public static EtudiantBusiness etB = new EtudiantBusiness();
    public static OptionBusiness opB = new OptionBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (etB.addEtudiant(etudiant)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> liste = etB.getAllEtudiants();
        return Response.status(Response.Status.OK).entity(liste).build();
    }

    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(
            @PathParam("identifiant") String identifiant) {

        Etudiant etudiant = etB.getEtudiantByIdentifiant(identifiant);

        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(etudiant).build();
    }

    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(
            @PathParam("identifiant") String identifiant) {

        if (etB.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(
            @PathParam("identifiant") String identifiant,
            Etudiant etudiant) {

        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Option option = opB.getOptionByCode(
                etudiant.getOption().getCodeOption());

        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        etudiant.setOption(option);

        if (etB.updateEtudiant(identifiant, etudiant)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantsByOption(
            @QueryParam("codeOption") int codeOption) {

        Option option = opB.getOptionByCode(codeOption);

        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Etudiant> liste = etB.getEtudiantsByOption(option);

        return Response.status(Response.Status.OK)
                .entity(liste)
                .build();
    }

}
