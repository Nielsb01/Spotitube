package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistDataMapperTest {
    private static final int ID = 1;
    private static final String NAME = "name";
    private static final boolean OWNER = true;
    private static final int DUR = 100;

    private PlaylistDataMapper sut;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp() {
        sut = new PlaylistDataMapper();
        mockedResultSet = mock(ResultSet.class);
    }

    @Test
    void testToDTO() throws SQLException {
        //Arrange
        var playlistDTO = new PlaylistDTO(ID, NAME, OWNER);
        var expected = new PlaylistsResponseDTO(Arrays.asList(playlistDTO), DUR);
        when(mockedResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockedResultSet.getInt("playlistId")).thenReturn(ID);
        when(mockedResultSet.getString("name")).thenReturn(NAME);
        when(mockedResultSet.getBoolean("owner")).thenReturn(OWNER);
        when(mockedResultSet.getInt("dur")).thenReturn(DUR);

        //Act
        var actualResult = sut.toDTO(mockedResultSet);

        //Assert
        assertEquals(expected.getLength(), actualResult.getLength());
        assertEquals(expected.getPlaylists().get(0).getName(), actualResult.getPlaylists().get(0).getName());
    }
}
