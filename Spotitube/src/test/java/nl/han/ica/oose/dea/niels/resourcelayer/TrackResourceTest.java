package nl.han.ica.oose.dea.niels.resourcelayer;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;
import nl.han.ica.oose.dea.niels.servicelayer.TrackService;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.ITrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class TrackResourceTest {
    private static final int FORPLAYLISTID = 1;
    private static final String TOKEN = "123456abcd";
    private TrackResource sut;
    private ITrackService mockedTrackService;

    @BeforeEach
    void setUp() {
        sut = new TrackResource();
        mockedTrackService = mock(TrackService.class);
        sut.setTrackService(mockedTrackService);
    }

    @Test
    void testGetAllTracksValidRequest() {
        //Arrange
        TracksResponseDTO tracksDTO = new TracksResponseDTO(new ArrayList<>());

        when(mockedTrackService.getAllTracksForPlaylist(FORPLAYLISTID, TOKEN)).thenReturn(tracksDTO);
        //Act
        Response actualResult = sut.getAllTracks(TOKEN, FORPLAYLISTID);

        //Assert
        verify(mockedTrackService).getAllTracksForPlaylist(FORPLAYLISTID, TOKEN);
        assertEquals(200, actualResult.getStatus());
        assertEquals(tracksDTO, actualResult.getEntity());
    }
}
