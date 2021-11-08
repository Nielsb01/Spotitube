package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDataMapper implements IDataMapper<PlaylistsResponseDTO>{
    @Override
    public PlaylistsResponseDTO toDTO(ResultSet rs) throws SQLException {
        List<PlaylistDTO> playlists = new ArrayList<>();
        int totalPlaylistTime = 0;
        while (rs.next()) {
            playlists.add(new PlaylistDTO(
                    rs.getInt("playlistId"),
                    rs.getString("name"),
                    rs.getBoolean("owner")));
            totalPlaylistTime = rs.getInt("dur");
        }
        return new PlaylistsResponseDTO(playlists, totalPlaylistTime);
    }
}
