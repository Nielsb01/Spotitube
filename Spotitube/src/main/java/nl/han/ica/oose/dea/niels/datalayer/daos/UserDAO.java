package nl.han.ica.oose.dea.niels.datalayer.daos;

import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.IUserDAO;
import nl.han.ica.oose.dea.niels.datalayer.datamappers.IDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.IConnection;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {
    private IConnection dbConnection;
    private IDataMapper<LoginResponseDTO> userDataMapper;
    private IDataMapper<LoginDTO> loginDataMapper;

    @Inject
    public void setUserDataMapper(IDataMapper<LoginResponseDTO> userDataMapper) {
        this.userDataMapper = userDataMapper;
    }

    @Inject
    public void setLoginDataMapper(IDataMapper<LoginDTO> loginDataMapper) {
        this.loginDataMapper = loginDataMapper;
    }

    @Inject
    public void setDbConnection(IConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public LoginDTO getUserLogin(String user){
        try (PreparedStatement ps = getUserPs(user);
             ResultSet rs = ps.executeQuery();)
        {
            return loginDataMapper.toDTO(rs);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public LoginResponseDTO getUserInfo(String user){
        try (PreparedStatement ps = getUserPs(user);
             ResultSet rs = ps.executeQuery();)
        {
            return userDataMapper.toDTO(rs);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    protected PreparedStatement getUserPs(String user) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("SELECT * FROM user WHERE user = ?");
        ps.setString(1, user);
        return ps;
    }
}

