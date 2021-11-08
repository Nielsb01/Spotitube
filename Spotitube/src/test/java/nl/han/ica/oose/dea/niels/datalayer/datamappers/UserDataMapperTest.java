package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDataMapperTest {
    private static final String USER = "niels borkes";
    private static final String TOKEN = "123456abcd";

    private UserDataMapper sut;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp(){
        sut = new UserDataMapper();
        mockedResultSet = mock(ResultSet.class);
    }

    @Test
    void testToDTO() throws SQLException {
        //Arrange
        var expected = new LoginResponseDTO(USER, TOKEN);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockedResultSet.getString("username")).thenReturn(USER);
        when(mockedResultSet.getString("token")).thenReturn(TOKEN);

        //Act
        var actualResult = sut.toDTO(mockedResultSet);

        //Assert
        assertEquals(expected.getUser(), actualResult.getUser());
        assertEquals(expected.getToken(), actualResult.getToken());
    }
}
