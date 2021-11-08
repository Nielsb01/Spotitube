package nl.han.ica.oose.dea.niels.servicelayer;

import nl.han.ica.oose.dea.niels.datalayer.daos.PlaylistDAO;
import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.IPlaylistDAO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class PlaylistServiceTest {
    private static final int ID = 1;
    private static final String TOKEN = "123456abcd";
    private PlaylistService sut;
    private IPlaylistDAO mockedPlaylistDAO;

    private PlaylistsResponseDTO playlistsResponseDTO;
    private PlaylistDTO playlistDTO;
    private TrackDTO trackDTO;

    @BeforeEach
    void setUp() {
        sut = new PlaylistService();
        mockedPlaylistDAO = mock(PlaylistDAO.class);
        sut.setUserDAO(mockedPlaylistDAO);

        trackDTO = new TrackDTO(ID, "title", "artist", 0, "album",
                0, "date", "description", true);
        playlistDTO = new PlaylistDTO(ID, "name", true);
        playlistDTO.setTracks(Arrays.asList(trackDTO,trackDTO));
        playlistsResponseDTO = new PlaylistsResponseDTO(Arrays.asList(playlistDTO), 999);
    }

    @Test
    void testGetAllForToken() {
        //Arrange
        when(mockedPlaylistDAO.getAll(TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        PlaylistsResponseDTO actualResult = sut.getAll(TOKEN);

        //Assert
        verify(mockedPlaylistDAO).getAll(TOKEN);
        assertEquals(playlistsResponseDTO, actualResult);
    }

    @Test
    void testDeletePlaylistForToken() {
        //Arrange
        when(mockedPlaylistDAO.getAll(TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        PlaylistsResponseDTO actualResult = sut.deletePlaylist(ID, TOKEN);

        //Assert
        verify(mockedPlaylistDAO).getAll(TOKEN);
        verify(mockedPlaylistDAO).delete(ID, TOKEN);
        assertEquals(playlistsResponseDTO, actualResult);
    }

    @Test
    void testAddPlaylistForToken() {
        //Arrange
        when(mockedPlaylistDAO.getAll(TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        PlaylistsResponseDTO actualResult = sut.addPlaylist(playlistDTO, TOKEN);

        //Assert
        verify(mockedPlaylistDAO).getAll(TOKEN);
        verify(mockedPlaylistDAO).add(playlistDTO, TOKEN);
        assertEquals(playlistsResponseDTO, actualResult);
    }

    @Test
    void testEditPlaylistForToken() {
        //Arrange
        when(mockedPlaylistDAO.getAll(TOKEN)).thenReturn(playlistsResponseDTO);

        //Act
        PlaylistsResponseDTO actualResult = sut.editPlaylist(playlistDTO, ID, TOKEN);

        //Assert
        verify(mockedPlaylistDAO).getAll(TOKEN);
        verify(mockedPlaylistDAO).edit(playlistDTO, ID, TOKEN);
        assertEquals(playlistsResponseDTO, actualResult);
    }
}
