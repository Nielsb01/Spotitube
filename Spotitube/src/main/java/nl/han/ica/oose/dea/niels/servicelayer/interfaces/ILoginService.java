package nl.han.ica.oose.dea.niels.servicelayer.interfaces;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;

public interface ILoginService {
    LoginResponseDTO checkLogin(LoginDTO loginDTO);
}
