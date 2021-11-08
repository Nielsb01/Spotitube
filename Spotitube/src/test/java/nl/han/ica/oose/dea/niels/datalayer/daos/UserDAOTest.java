package nl.han.ica.oose.dea.niels.datalayer.daos;

import nl.han.ica.oose.dea.niels.datalayer.datamappers.IDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.datamappers.LoginDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.datamappers.UserDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.ConnectionSQL;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.IConnection;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserDAOTest {
    private static final String USER = "niels";
    private static final LoginDTO LOGIN_DTO = new LoginDTO(USER, "koekje123");
    private static final LoginResponseDTO LOGIN_RESPONSE_DTO = new LoginResponseDTO("niels borkes", "123456abcd");

    private UserDAO sut;
    private IConnection mockedConnectionSQL;
    private IDataMapper<LoginDTO> mockedLoginDataMapper;
    private IDataMapper<LoginResponseDTO> mockedUserDataMapper;

    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp() {
        sut = new UserDAO();
        mockedConnectionSQL = mock(ConnectionSQL.class);
        sut.setDbConnection(mockedConnectionSQL);
        mockedLoginDataMapper = mock(LoginDataMapper.class);
        sut.setLoginDataMapper(mockedLoginDataMapper);
        mockedUserDataMapper = mock(UserDataMapper.class);
        sut.setUserDataMapper(mockedUserDataMapper);
        mockedConnection = mock(Connection.class);
        mockedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
    }

    @Test
    void testGetUserLoginCallsLoginDataMapper() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setString(1, USER);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedLoginDataMapper.toDTO(mockedResultSet)).thenReturn(LOGIN_DTO);

        //Act
        var actualResult = sut.getUserLogin(USER);

        //Assert
        verify(mockedStatement, atLeast(1)).executeQuery();
        verify(mockedLoginDataMapper, atLeast(1)).toDTO(mockedResultSet);
        assertEquals(LOGIN_DTO, actualResult);
    }

    @Test
    void testGetUserLoginThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.getUserLogin(USER);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testGetUserInfoCallsLoginDataMapper() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setString(1, USER);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedUserDataMapper.toDTO(mockedResultSet)).thenReturn(LOGIN_RESPONSE_DTO);

        //Act
        var actualResult = sut.getUserInfo(USER);

        //Assert
        verify(mockedStatement, atLeast(1)).executeQuery();
        verify(mockedUserDataMapper, atLeast(1)).toDTO(mockedResultSet);
        assertEquals(LOGIN_RESPONSE_DTO, actualResult);
    }

    @Test
    void testGetUserInfoThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.getUserInfo(USER);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }
}
