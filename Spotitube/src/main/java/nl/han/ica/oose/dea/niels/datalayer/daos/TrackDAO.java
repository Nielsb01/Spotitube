package nl.han.ica.oose.dea.niels.datalayer.daos;

import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.ITrackDAO;
import nl.han.ica.oose.dea.niels.datalayer.datamappers.IDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.IConnection;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDAO implements ITrackDAO {
    private IConnection dbConnection;
    private IDataMapper<TracksResponseDTO> tracksDataMapper;

    @Inject
    public void setTracksDataMapper(IDataMapper<TracksResponseDTO> tracksDataMapper) {
        this.tracksDataMapper = tracksDataMapper;
    }

    @Inject
    public void setDbConnection(IConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public TracksResponseDTO getAllTracksForPlaylist(int forPlaylistId, String token){
        try (PreparedStatement ps = getAllTracksNotInPlaylistPs(forPlaylistId, token);
             ResultSet rs = ps.executeQuery();)
        {
            return tracksDataMapper.toDTO(rs);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public TracksResponseDTO getAllTracksInPlaylist(int playlistId, String token) {
        try (PreparedStatement ps = getAllTracksInPlaylistPs(playlistId, token);
             ResultSet rs = ps.executeQuery();)
        {
            return tracksDataMapper.toDTO(rs);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void deleteTrackFromPlaylist(int playlistId, int trackId, String token) {
        try (PreparedStatement ps = deleteTrackFromPlaylistPs(playlistId, trackId, token);)
        {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public TrackDTO getTrack(int trackId) {
        try (PreparedStatement ps = getTrackPs(trackId);
             ResultSet rs = ps.executeQuery();){
            return tracksDataMapper.toDTO(rs).getTracks().get(0);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void updateTrack(TrackDTO trackDTO) {
        try (PreparedStatement ps = updatePs(trackDTO);)
        {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void addTrackToPlaylist(int playlistId, int trackId) {
        try (PreparedStatement ps = addTrackToPlaylistPs(playlistId, trackId);)
        {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private PreparedStatement getAllTracksNotInPlaylistPs(int forPlaylistId, String token) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("SELECT * FROM track T WHERE T.trackId NOT IN(SELECT TIP.trackId FROM playlist P JOIN tracksinplaylist TIP ON p.playlistId = tip.playlistId WHERE p.playlistId = ? AND p.token = ? )");
        ps.setInt(1, forPlaylistId);
        ps.setString(2, token);
        return ps;
    }

    private PreparedStatement getAllTracksInPlaylistPs(int playlistId, String token) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("SELECT * FROM track T WHERE T.trackId IN(SELECT TIP.trackId FROM playlist P JOIN tracksinplaylist TIP ON p.playlistId = tip.playlistId WHERE p.playlistId = ? AND p.token = ? )");
        ps.setInt(1, playlistId);
        ps.setString(2, token);
        return ps;
    }

    private PreparedStatement deleteTrackFromPlaylistPs(int playlistId, int trackId, String token) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("DELETE FROM tracksinplaylist WHERE playlistId = ? AND trackId = ? AND EXISTS(SELECT * FROM playlist WHERE playlistId = ? AND token = ?)");
        ps.setInt(1, playlistId);
        ps.setInt(2, trackId);
        ps.setInt(3, playlistId);
        ps.setString(4, token);
        return ps;
    }

    private PreparedStatement getTrackPs(int trackId) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("SELECT * FROM track WHERE trackId = ?");
        ps.setInt(1, trackId);
        return ps;
    }

    private PreparedStatement updatePs(TrackDTO trackDTO) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("UPDATE track SET offlineAvailable = ? WHERE trackId = ?");
        ps.setBoolean(1, trackDTO.isOfflineAvailable());
        ps.setInt(2, trackDTO.getId());
        return ps;
    }

    private PreparedStatement addTrackToPlaylistPs(int playlistId, int trackId) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("INSERT INTO tracksinplaylist VALUES(?, ?)");
        ps.setInt(1, trackId);
        ps.setInt(2, playlistId);
        return ps;
    }
}
