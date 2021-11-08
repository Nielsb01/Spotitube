package nl.han.ica.oose.dea.niels.datalayer.daos.interfaces;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;

public interface IUserDAO {
    LoginDTO getUserLogin(String user);
    LoginResponseDTO getUserInfo(String user);
}
