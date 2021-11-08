package nl.han.ica.oose.dea.niels.resourcelayer;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.ILoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/login")
public class LoginResource {
    private ILoginService loginService;

    @Inject
    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginResponse(LoginDTO loginDTO) {
        return Response.status(200).entity(loginService.checkLogin(loginDTO)).build();
    }
}
