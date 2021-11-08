package nl.han.ica.oose.dea.niels.datalayer.daos;

import nl.han.ica.oose.dea.niels.datalayer.datamappers.IDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.datamappers.TracksDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.ConnectionSQL;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.IConnection;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

class TrackDAOTest {
    private static final String TOKEN = "123456abcd";
    private static final int PLAYLIST_ID = 1;
    private static final int TRACK_ID = 2;
    private static final TrackDTO TRACK_DTO = new TrackDTO(TRACK_ID, "title", "artist", 0, "album",
            0, "date", "description", true);

    private TrackDAO sut;
    private IConnection mockedConnectionSQL;
    private IDataMapper<TracksResponseDTO> mockedTracksDataMapper;

    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private ResultSet mockedResultSet;

    private TracksResponseDTO tracksResponseDTO = new TracksResponseDTO();

    @BeforeEach
    void setUp() {
        sut = new TrackDAO();
        mockedConnectionSQL = mock(ConnectionSQL.class);
        sut.setDbConnection(mockedConnectionSQL);
        mockedTracksDataMapper = mock(TracksDataMapper.class);
        sut.setTracksDataMapper(mockedTracksDataMapper);
        mockedConnection = mock(Connection.class);
        mockedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);

        tracksResponseDTO.setTracks(Arrays.asList(TRACK_DTO));
    }


    @Test
    void testGetAllTracksForPlaylistCallsTracksDataMapper() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setInt(1, PLAYLIST_ID);
        doNothing().when(mockedStatement).setString(2, TOKEN);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedTracksDataMapper.toDTO(mockedResultSet)).thenReturn(tracksResponseDTO);

        //Act
        var actualResult = sut.getAllTracksForPlaylist(PLAYLIST_ID, TOKEN);

        //Assert
        verify(mockedStatement, atLeast(1)).executeQuery();
        verify(mockedTracksDataMapper, atLeast(1)).toDTO(mockedResultSet);
        assertEquals(tracksResponseDTO, actualResult);
    }

    @Test
    void testGetAllTracksForPlaylistThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.getAllTracksForPlaylist(PLAYLIST_ID, TOKEN);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testGetAllTracksInPlaylistCallsTracksDataMapper() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setInt(1, PLAYLIST_ID);
        doNothing().when(mockedStatement).setString(2, TOKEN);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedTracksDataMapper.toDTO(mockedResultSet)).thenReturn(tracksResponseDTO);

        //Act
        var actualResult = sut.getAllTracksInPlaylist(PLAYLIST_ID, TOKEN);

        //Assert
        verify(mockedStatement, atLeast(1)).executeQuery();
        verify(mockedTracksDataMapper, atLeast(1)).toDTO(mockedResultSet);
        assertEquals(tracksResponseDTO, actualResult);
    }

    @Test
    void testGetAllTracksInPlaylistThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.getAllTracksInPlaylist(PLAYLIST_ID, TOKEN);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testDeleteTrackFromPlaylistCallsExecute() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setInt(1, PLAYLIST_ID);
        doNothing().when(mockedStatement).setInt(2, TRACK_ID);
        doNothing().when(mockedStatement).setInt(3, PLAYLIST_ID);
        doNothing().when(mockedStatement).setString(4, TOKEN);
        doReturn(1).when(mockedStatement).executeUpdate();

        //Act
        sut.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);

        //Assert
        verify(mockedStatement, atLeast(1)).executeUpdate();
    }

    @Test
    void testDeleteTrackFromPlaylistThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.deleteTrackFromPlaylist(PLAYLIST_ID, TRACK_ID, TOKEN);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testGetTrackCallsTracksDataMapper() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setInt(1, TRACK_ID);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedTracksDataMapper.toDTO(mockedResultSet)).thenReturn(tracksResponseDTO);

        //Act
        var actualResult = sut.getTrack(TRACK_ID);

        //Assert
        verify(mockedStatement, atLeast(1)).executeQuery();
        verify(mockedTracksDataMapper, atLeast(1)).toDTO(mockedResultSet);
        assertEquals(TRACK_DTO, actualResult);
    }

    @Test
    void testGetTrackThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.getTrack(TRACK_ID);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testUpdateTrackCallsExecute() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setBoolean(1, TRACK_DTO.isOfflineAvailable());
        doNothing().when(mockedStatement).setInt(2, TRACK_ID);
        doReturn(1).when(mockedStatement).executeUpdate();

        //Act
        sut.updateTrack(TRACK_DTO);

        //Assert
        verify(mockedStatement, atLeast(1)).executeUpdate();
    }

    @Test
    void testUpdateTrackThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.updateTrack(TRACK_DTO);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testAddTrackToPlaylistCallsExecute() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setInt(1, TRACK_ID);
        doNothing().when(mockedStatement).setInt(2, PLAYLIST_ID);
        doReturn(1).when(mockedStatement).executeUpdate();

        //Act
        sut.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID);

        //Assert
        verify(mockedStatement, atLeast(1)).executeUpdate();
    }

    @Test
    void testAddTrackToPlaylistThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.addTrackToPlaylist(PLAYLIST_ID, TRACK_ID);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }
}
