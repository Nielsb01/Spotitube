package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDataMapper implements IDataMapper<LoginDTO> {
    @Override
    public LoginDTO toDTO(ResultSet rs) throws SQLException {
        LoginDTO loginDTO = new LoginDTO();
        while (rs.next()) {
            loginDTO = new LoginDTO(
                    rs.getString("user"),
                    rs.getString("passwordHashed")
            );
        }
        return loginDTO;
    }
}
