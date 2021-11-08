package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataMapper implements IDataMapper<LoginResponseDTO>{
    @Override
    public LoginResponseDTO toDTO(ResultSet rs) throws SQLException {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        while (rs.next()) {
            loginResponseDTO = new LoginResponseDTO(
                    rs.getString("username"),
                    rs.getString("token")
            );
        }
        return loginResponseDTO;
    }
}
