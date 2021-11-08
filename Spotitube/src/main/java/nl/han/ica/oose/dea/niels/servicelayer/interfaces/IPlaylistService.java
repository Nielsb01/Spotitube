package nl.han.ica.oose.dea.niels.servicelayer.interfaces;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;

public interface IPlaylistService {
    PlaylistsResponseDTO getAll(String token);
    PlaylistsResponseDTO deletePlaylist(int playlistId, String token);
    PlaylistsResponseDTO addPlaylist(PlaylistDTO playlistDTO, String token);
    PlaylistsResponseDTO editPlaylist(PlaylistDTO playlistDTO, int playlistId, String token);
}
