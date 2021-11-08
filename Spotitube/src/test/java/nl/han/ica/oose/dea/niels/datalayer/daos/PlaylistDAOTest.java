package nl.han.ica.oose.dea.niels.datalayer.daos;

import nl.han.ica.oose.dea.niels.datalayer.datamappers.IDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.datamappers.PlaylistDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.ConnectionSQL;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.IConnection;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;
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

class PlaylistDAOTest {
    private static final String TOKEN = "123456abcd";
    private static final int ID = 1;
    private static final PlaylistDTO PLAYLIST_DTO = new PlaylistDTO(ID, "name", true);
    private static final PlaylistsResponseDTO PLAYLISTS_RESPONSE_DTO = new PlaylistsResponseDTO(Arrays.asList(PLAYLIST_DTO), 999);

    private PlaylistDAO sut;
    private IConnection mockedConnectionSQL;
    private IDataMapper<PlaylistsResponseDTO> mockedPlaylistDataMapper;

    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setUp() {
        sut = new PlaylistDAO();
        mockedConnectionSQL = mock(ConnectionSQL.class);
        sut.setDbConnection(mockedConnectionSQL);
        mockedPlaylistDataMapper = mock(PlaylistDataMapper.class);
        sut.setPlaylistDataMapper(mockedPlaylistDataMapper);
        mockedConnection = mock(Connection.class);
        mockedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
    }


    @Test
    void testGetAllCallsPlaylistDataMapper() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setString(1, TOKEN);
        doNothing().when(mockedStatement).setString(2, TOKEN);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedPlaylistDataMapper.toDTO(mockedResultSet)).thenReturn(PLAYLISTS_RESPONSE_DTO);

        //Act
        var actualResult = sut.getAll(TOKEN);

        //Assert
        verify(mockedStatement, atLeast(1)).executeQuery();
        verify(mockedPlaylistDataMapper, atLeast(1)).toDTO(mockedResultSet);
        assertEquals(PLAYLISTS_RESPONSE_DTO, actualResult);
    }

    @Test
    void testGetAllThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.getAll(TOKEN);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testDeleteCallsExecute() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setInt(1, ID);
        doNothing().when(mockedStatement).setString(2, TOKEN);
        doReturn(1).when(mockedStatement).executeUpdate();

        //Act
        sut.delete(ID, TOKEN);

        //Assert
        verify(mockedStatement, atLeast(1)).executeUpdate();
    }

    @Test
    void testDeleteThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.delete(ID, TOKEN);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testAddCallsExecute() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setString(1, TOKEN);
        doNothing().when(mockedStatement).setString(2, PLAYLIST_DTO.getName());
        doReturn(1).when(mockedStatement).executeUpdate();

        //Act
        sut.add(PLAYLIST_DTO, TOKEN);

        //Assert
        verify(mockedStatement, atLeast(1)).executeUpdate();
    }

    @Test
    void testAddThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.add(PLAYLIST_DTO, TOKEN);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }

    @Test
    void testEditCallsExecute() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
        doNothing().when(mockedStatement).setString(1, PLAYLIST_DTO.getName());
        doNothing().when(mockedStatement).setInt(2, ID);
        doNothing().when(mockedStatement).setString(3, TOKEN);
        doReturn(1).when(mockedStatement).executeUpdate();

        //Act
        sut.edit(PLAYLIST_DTO, ID, TOKEN);

        //Assert
        verify(mockedStatement, atLeast(1)).executeUpdate();
    }

    @Test
    void testEditThowsInternalServerErrorException() throws SQLException {
        //Arrange
        when(mockedConnectionSQL.getConnection()).thenThrow(new SQLException());

        //Act
        //Assert
        Exception ex = assertThrows(InternalServerErrorException.class, () -> {sut.edit(PLAYLIST_DTO, ID, TOKEN);});
        verify(mockedConnectionSQL, atLeast(1)).getConnection();
    }
}
