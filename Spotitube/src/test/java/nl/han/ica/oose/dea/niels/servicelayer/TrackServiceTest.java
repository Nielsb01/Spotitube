package nl.han.ica.oose.dea.niels.servicelayer;

import nl.han.ica.oose.dea.niels.datalayer.daos.TrackDAO;
import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.ITrackDAO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrackServiceTest {
    private static final String TOKEN = "123456abcd";
    private static final int PLAYLISTID = 1;
    private static final int TRACKID = 2;

    private TrackService sut;
    private ITrackDAO mockedTrackDAO;

    private TrackDTO trackDTO;
    private TracksResponseDTO tracksResponseDTO;


    @BeforeEach
    void setUp() {
        sut = new TrackService();
        mockedTrackDAO = mock(TrackDAO.class);
        sut.setTrackDAO(mockedTrackDAO);

        trackDTO = new TrackDTO(TRACKID, "title", "artist", 0, "album",
                0, "date", "description", true);
        tracksResponseDTO = new TracksResponseDTO();
        tracksResponseDTO.setTracks(Arrays.asList(trackDTO));
    }

    @Test
    void testGetAllTracksForPlaylist() {
        //Arrange
        when(mockedTrackDAO.getAllTracksForPlaylist(PLAYLISTID, TOKEN)).thenReturn(tracksResponseDTO);

        //Act
        TracksResponseDTO actualResult = sut.getAllTracksForPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(mockedTrackDAO).getAllTracksForPlaylist(PLAYLISTID, TOKEN);
        assertEquals(tracksResponseDTO, actualResult);
    }

    @Test
    void testGetTracksInPlaylist() {
        //Arrange
        when(mockedTrackDAO.getAllTracksInPlaylist(PLAYLISTID, TOKEN)).thenReturn(tracksResponseDTO);

        //Act
        TracksResponseDTO actualResult = sut.getTracksInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(mockedTrackDAO).getAllTracksInPlaylist(PLAYLISTID, TOKEN);
        assertEquals(tracksResponseDTO, actualResult);
    }

    @Test
    void deleteTrackFromPlaylist() {
        //Arrange
        when(mockedTrackDAO.getAllTracksInPlaylist(PLAYLISTID, TOKEN)).thenReturn(tracksResponseDTO);

        //Act
        TracksResponseDTO actualResult = sut.deleteTrackFromPlaylist(PLAYLISTID, TRACKID, TOKEN);

        //Assert
        verify(mockedTrackDAO).getAllTracksInPlaylist(PLAYLISTID, TOKEN);
        verify(mockedTrackDAO).deleteTrackFromPlaylist(PLAYLISTID, TRACKID, TOKEN);
        assertEquals(tracksResponseDTO, actualResult);
    }

    @Test
    void testAddTrackToPlaylist() {
        //Arrange
        when(mockedTrackDAO.getAllTracksInPlaylist(PLAYLISTID, TOKEN)).thenReturn(tracksResponseDTO);
        when(mockedTrackDAO.getTrack(TRACKID)).thenReturn(trackDTO);

        //Act
        TracksResponseDTO actualResult = sut.addTrackToPlaylist(PLAYLISTID, trackDTO, TOKEN);

        //Assert
        verify(mockedTrackDAO).getAllTracksInPlaylist(PLAYLISTID, TOKEN);
        verify(mockedTrackDAO).updateTrack(trackDTO);
        verify(mockedTrackDAO).addTrackToPlaylist(PLAYLISTID, TRACKID);
        assertEquals(tracksResponseDTO, actualResult);
    }
}
