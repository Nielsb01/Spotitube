package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TracksDataMapper implements IDataMapper<TracksResponseDTO>{
    @Override
    public TracksResponseDTO toDTO(ResultSet rs) throws SQLException {
        List<TrackDTO> tracks = new ArrayList<>();
        while (rs.next()) {
            tracks.add(new TrackDTO(
                    rs.getInt("trackId"),
                    rs.getString("title"),
                    rs.getString("performer"),
                    rs.getInt("duration"),
                    rs.getString("album"),
                    rs.getInt("playcount"),
                    rs.getString("publicationDate"),
                    rs.getString("description"),
                    rs.getBoolean("offlineAvailable")
            ));
        }
        return new TracksResponseDTO(tracks);
    }
}
