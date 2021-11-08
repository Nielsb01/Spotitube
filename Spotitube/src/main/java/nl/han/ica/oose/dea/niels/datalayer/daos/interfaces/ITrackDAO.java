package nl.han.ica.oose.dea.niels.datalayer.daos.interfaces;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;

public interface ITrackDAO {
    TracksResponseDTO getAllTracksForPlaylist(int forPlaylistId, String token);
    TracksResponseDTO getAllTracksInPlaylist(int playlistId, String token);
    void deleteTrackFromPlaylist(int playlistId, int trackId, String token);
    TrackDTO getTrack(int trackId);
    void updateTrack(TrackDTO trackDTO);
    void addTrackToPlaylist(int playlistId, int trackId);
}
