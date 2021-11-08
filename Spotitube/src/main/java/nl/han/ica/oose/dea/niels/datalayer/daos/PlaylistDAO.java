package nl.han.ica.oose.dea.niels.datalayer.daos;

import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.IPlaylistDAO;
import nl.han.ica.oose.dea.niels.datalayer.datamappers.IDataMapper;
import nl.han.ica.oose.dea.niels.datalayer.dbconnection.IConnection;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO implements IPlaylistDAO {
    private IConnection dbConnection;
    private IDataMapper<PlaylistsResponseDTO> playlistDataMapper;

    @Inject
    public void setPlaylistDataMapper(IDataMapper<PlaylistsResponseDTO> playlistDataMapper) {
        this.playlistDataMapper = playlistDataMapper;
    }

    @Inject
    public void setDbConnection(IConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public PlaylistsResponseDTO getAll(String token){
        try (PreparedStatement ps = getAllPs(token);
             ResultSet rs = ps.executeQuery();)
        {
            return playlistDataMapper.toDTO(rs);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void delete(int playlistId, String token){
        try (PreparedStatement ps = deletePs(playlistId, token);)
        {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void add(PlaylistDTO playlistDTO, String token){
        try (PreparedStatement ps = addPs(playlistDTO, token);)
        {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void edit(PlaylistDTO playlistDTO, int playlistId, String token){
        try (PreparedStatement ps = editPs(playlistDTO, playlistId, token);)
        {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private PreparedStatement getAllPs(String token) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("SELECT playlistId, name, owner, (SELECT SUM(t.duration) FROM playlist P JOIN tracksinplaylist TIP ON p.playlistId = tip.playlistId JOIN track T ON tip.trackId = t.trackId WHERE token = ?) as dur FROM playlist WHERE token = ?");
        ps.setString(1, token);
        ps.setString(2, token);
        return ps;
    }

    private PreparedStatement deletePs(int playlistId,  String token) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("DELETE FROM playlist WHERE playlistId = ? AND token = ?");
        ps.setInt(1, playlistId);
        ps.setString(2, token);
        return ps;
    }

    private PreparedStatement addPs(PlaylistDTO playlistDTO, String token) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("INSERT INTO playlist(token, name, owner) VALUES(?, ?, true)");
        ps.setString(1, token);
        ps.setString(2, playlistDTO.getName());
        return ps;
    }

    private PreparedStatement editPs(PlaylistDTO playlistDTO, int playlistId, String token) throws SQLException {
        PreparedStatement ps = dbConnection.getConnection().prepareStatement("UPDATE playlist SET name = ? WHERE playlistId = ? AND token = ?");
        ps.setString(1, playlistDTO.getName());
        ps.setInt(2, playlistId);
        ps.setString(3, token);
        return ps;
    }
}
