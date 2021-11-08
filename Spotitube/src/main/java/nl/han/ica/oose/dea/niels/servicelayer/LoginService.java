package nl.han.ica.oose.dea.niels.servicelayer;

import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.IUserDAO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.ILoginService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;

public class LoginService implements ILoginService {
    private IUserDAO userDAO;

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public LoginResponseDTO checkLogin(LoginDTO loginDTO){
        if (DigestUtils.sha256Hex(loginDTO.getPassword()).equals(userDAO.getUserLogin(loginDTO.getUser()).getPassword())) {
            return userDAO.getUserInfo(loginDTO.getUser());
        } else {
            throw new NotAuthorizedException("The combination username, password incorrect or user not existing!");
        }
    }
}
