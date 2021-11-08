package nl.han.ica.oose.dea.niels.resourcelayer;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;
import nl.han.ica.oose.dea.niels.servicelayer.PlaylistService;
import nl.han.ica.oose.dea.niels.servicelayer.TrackService;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.IPlaylistService;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.ITrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlaylistResourceTest {
    private static final String TOKEN = "123456abcd";
    private static final int PLAYLISTID = 1;
    private static final int TRACKID = 2;

    private PlaylistResource sut;
    private IPlaylistService mockedPlaylistService;
    private ITrackService mockedTrackService;

    private PlaylistsResponseDTO playlistsResponseDTO;
    private PlaylistDTO playlistDTO;

    private TrackDTO trackDTO;
    private TracksResponseDTO tracksResponseDTO;

    @BeforeEach
    void setUp() {
    sut = new PlaylistResource();
    mockedPlaylistService = mock(PlaylistService.class);
    sut.setPlaylistService(mockedPlaylistService);
    mockedTrackService = mock(TrackService.class);
    sut.setTrackService(mockedTrackService);

    trackDTO = new TrackDTO(TRACKID, "title", "artist", 0, "album",
            0, "date", "description", true);
    tracksResponseDTO = new TracksResponseDTO();
    tracksResponseDTO.setTracks(Arrays.asList(trackDTO));
    playlistDTO = new PlaylistDTO(PLAYLISTID, "name", true);
    playlistDTO.setTracks(Arrays.asList(trackDTO));
    playlistsResponseDTO = new PlaylistsResponseDTO(Arrays.asList(playlistDTO), 999);
    }

    @Test
    void testGetPlaylistsValidRequest() {
        //Arrange
        when(mockedPlaylistService.getAll(TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        Response actualResult = sut.getPlaylists(TOKEN);

        //Assert
        verify(mockedPlaylistService).getAll(TOKEN);
        assertEquals(200, actualResult.getStatus());
        assertEquals(playlistsResponseDTO, actualResult.getEntity());
    }

    @Test
    void testDeletePlaylistsValidRequest() {
        //Arrange
        when(mockedPlaylistService.deletePlaylist(PLAYLISTID, TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        Response actualResult = sut.deletePlaylists(PLAYLISTID, TOKEN);

        //Assert
        verify(mockedPlaylistService).deletePlaylist(PLAYLISTID, TOKEN);
        assertEquals(200, actualResult.getStatus());
        assertEquals(playlistsResponseDTO, actualResult.getEntity());
    }

    @Test
    void testAddPlaylistValidRequest() {
        //Arrange
        when(mockedPlaylistService.addPlaylist(playlistDTO, TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        Response actualResult = sut.addPlaylist(playlistDTO, TOKEN);

        //Assert
        verify(mockedPlaylistService).addPlaylist(playlistDTO, TOKEN);
        assertEquals(201, actualResult.getStatus());
        assertEquals(playlistsResponseDTO, actualResult.getEntity());
    }

    @Test
    void testEditPlaylistValidRequest() {
        //Arrange
        when(mockedPlaylistService.editPlaylist(playlistDTO, PLAYLISTID, TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        Response actualResult = sut.editPlaylist(playlistDTO, PLAYLISTID, TOKEN);

        //Assert
        verify(mockedPlaylistService).editPlaylist(playlistDTO, PLAYLISTID, TOKEN);
        assertEquals(200, actualResult.getStatus());
        assertEquals(playlistsResponseDTO, actualResult.getEntity());
    }

    @Test
    void getTracksOfPlaylistValidRequest() {
        //Arrange
        when(mockedTrackService.getTracksInPlaylist(PLAYLISTID, TOKEN)).thenReturn(tracksResponseDTO);

        //Act
        Response actualResult = sut.getTracksOfPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(mockedTrackService).getTracksInPlaylist(PLAYLISTID, TOKEN);
        assertEquals(200, actualResult.getStatus());
        assertEquals(tracksResponseDTO, actualResult.getEntity());
    }

    @Test
    void testDeleteTrackFromPlaylistValidRequest() {
        //Arrange
        when(mockedTrackService.deleteTrackFromPlaylist(PLAYLISTID, TRACKID, TOKEN)).thenReturn(tracksResponseDTO);

        //Act
        Response actualResult = sut.deleteTrackFromPlaylist(PLAYLISTID, TRACKID, TOKEN);

        //Assert
        verify(mockedTrackService).deleteTrackFromPlaylist(PLAYLISTID, TRACKID, TOKEN);
        assertEquals(200, actualResult.getStatus());
        assertEquals(tracksResponseDTO, actualResult.getEntity());
    }

    @Test
    void testAddTrackToPlaylistValidRequest() {
        //Arrange
        when(mockedTrackService.addTrackToPlaylist(PLAYLISTID, trackDTO, TOKEN)).thenReturn(tracksResponseDTO);

        //Act
        Response actualResult = sut.addTrackToPlaylist(PLAYLISTID, trackDTO, TOKEN);

        //Assert
        verify(mockedTrackService).addTrackToPlaylist(PLAYLISTID, trackDTO, TOKEN);
        assertEquals(201, actualResult.getStatus());
        assertEquals(tracksResponseDTO, actualResult.getEntity());
    }
}
