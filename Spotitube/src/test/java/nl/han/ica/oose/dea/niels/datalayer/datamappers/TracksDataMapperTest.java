package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TracksDataMapperTest {
    public static final int TRACKID = 1;
    public static final String TITLE = "title";
    public static final String ARTIST = "artist";
    public static final String ALBUM = "album";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final boolean OFFLINE_AVAILABLE = true;

    private TracksDataMapper sut;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp() {
        sut = new TracksDataMapper();
        mockedResultSet = mock(ResultSet.class);
    }

    @Test
    void testToDTO() throws SQLException {
        //Arrange
        var trackDTO = new TrackDTO(TRACKID, TITLE, ARTIST, TRACKID, ALBUM,
                TRACKID, DATE, DESCRIPTION, OFFLINE_AVAILABLE);
        var expected = new TracksResponseDTO(Arrays.asList(trackDTO));
        when(mockedResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockedResultSet.getInt("trackId")).thenReturn(TRACKID);
        when(mockedResultSet.getString("title")).thenReturn(TITLE);
        when(mockedResultSet.getString("performer")).thenReturn(ARTIST);
        when(mockedResultSet.getInt("duration")).thenReturn(TRACKID);
        when(mockedResultSet.getString("album")).thenReturn(ALBUM);
        when(mockedResultSet.getInt("playcount")).thenReturn(TRACKID);
        when(mockedResultSet.getString("publicationDate")).thenReturn(DATE);
        when(mockedResultSet.getString("description")).thenReturn(DESCRIPTION);
        when(mockedResultSet.getBoolean("offlineAvailable")).thenReturn(OFFLINE_AVAILABLE);

        //Act
        var actualResult = sut.toDTO(mockedResultSet);

        //Assert
        assertEquals(expected.getTracks().get(0).getId(), actualResult.getTracks().get(0).getId());
        assertEquals(expected.getTracks().get(0).getTitle(), actualResult.getTracks().get(0).getTitle());
    }
}
