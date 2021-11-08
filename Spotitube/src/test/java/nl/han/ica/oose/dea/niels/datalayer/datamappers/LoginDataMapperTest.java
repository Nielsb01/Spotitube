package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginDataMapperTest {
    private static final String USER = "niels";
    private static final String PASSWORD = "hAsHeDpAsSwOrD";

    private LoginDataMapper sut;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp(){
        sut = new LoginDataMapper();
        mockedResultSet = mock(ResultSet.class);
    }

    @Test
    void testToDTO() throws SQLException {
        //Arrange
        var expected = new LoginDTO(USER, PASSWORD);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockedResultSet.getString("user")).thenReturn(USER);
        when(mockedResultSet.getString("passwordHashed")).thenReturn(PASSWORD);

        //Act
        var actualResult = sut.toDTO(mockedResultSet);

        //Assert
        assertEquals(expected.getUser(), actualResult.getUser());
        assertEquals(expected.getPassword(), actualResult.getPassword());
    }
}
