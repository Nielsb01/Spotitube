package nl.han.ica.oose.dea.niels.servicelayer;

import nl.han.ica.oose.dea.niels.datalayer.daos.UserDAO;
import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.IUserDAO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotAuthorizedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LoginServiceTest {
    private static final String USER = "niels";

    private LoginService sut;
    private IUserDAO mockedUserDAO;

    private LoginDTO loginDTO;
    private LoginDTO databaseLogin;
    private LoginResponseDTO loginResponseDTO;

    @BeforeEach
    void setUp() {
        sut = new LoginService();
        mockedUserDAO = mock(UserDAO.class);
        sut.setUserDAO(mockedUserDAO);

        loginDTO = new LoginDTO(USER, "koekje123");
        databaseLogin = new LoginDTO(USER, "2869103419ce0e9405faf2153fbb014fa830a6da7978922a2049d0726e35a785");
        loginResponseDTO = new LoginResponseDTO("niels borkes", "123456abcd");
    }

    @Test
    void testCheckLoginValidUser() {
        //Arrange
        when(mockedUserDAO.getUserLogin(USER)).thenReturn(databaseLogin);
        when(mockedUserDAO.getUserInfo(USER)).thenReturn(loginResponseDTO);

        //Act
        LoginResponseDTO actualResult = sut.checkLogin(loginDTO);

        //Assert
        verify(mockedUserDAO).getUserLogin(USER);
        verify(mockedUserDAO).getUserInfo(USER);
        assertEquals(loginResponseDTO, actualResult);
    }

    @Test
    void testCheckLoginWrongUserThrowsException() {
        //Arrange
        when(mockedUserDAO.getUserLogin(USER)).thenReturn(new LoginDTO("nietNiels", "nietNielsZijnWachtwoord"));
        when(mockedUserDAO.getUserInfo(USER)).thenReturn(loginResponseDTO);

        //Act
        //Assert
        Exception ex = assertThrows(NotAuthorizedException.class, () -> {sut.checkLogin(loginDTO);});
    }
}
