package nl.han.ica.oose.dea.niels.servicelayer.interfaces;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;

public interface ITrackService {
    TracksResponseDTO getAllTracksForPlaylist(int forPlaylistId, String token);
    TracksResponseDTO getTracksInPlaylist(int playlistId, String token);
    TracksResponseDTO deleteTrackFromPlaylist(int playlistId, int trackId, String token);
    TracksResponseDTO addTrackToPlaylist(int playlistId, TrackDTO trackDTO, String token);
}
