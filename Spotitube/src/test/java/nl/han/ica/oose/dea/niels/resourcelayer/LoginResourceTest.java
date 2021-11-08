package nl.han.ica.oose.dea.niels.resourcelayer;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;
import nl.han.ica.oose.dea.niels.servicelayer.LoginService;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.ILoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginResourceTest {
    private LoginResource sut;
    private ILoginService mockedLoginService;

    @BeforeEach
    void setUp() {
        sut = new LoginResource();
        mockedLoginService = mock(LoginService.class);
        sut.setLoginService(mockedLoginService);
    }

    @Test
    void testLoginResponseValidLogin() {
        //Arrange
        final LoginDTO loginDTO = new LoginDTO("niels", "koekje123");
        final LoginResponseDTO loginResponseDTO = new LoginResponseDTO("niels borkes", "123456abcd");

        when(mockedLoginService.checkLogin(loginDTO)).thenReturn(loginResponseDTO);

        //Act
        Response actualResult = sut.loginResponse(loginDTO);

        //Assert
        verify(mockedLoginService).checkLogin(loginDTO);
        assertEquals(200, actualResult.getStatus());
        assertEquals(loginResponseDTO, actualResult.getEntity());
    }
}
