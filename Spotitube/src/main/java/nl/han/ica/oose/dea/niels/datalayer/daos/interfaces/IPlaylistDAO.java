package nl.han.ica.oose.dea.niels.datalayer.daos.interfaces;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;

public interface IPlaylistDAO {
    PlaylistsResponseDTO getAll(String token);
    void delete(int playlistId, String token);
    void add(PlaylistDTO playlistDTO, String token);
    void edit(PlaylistDTO playlistDTO, int playlistId, String token);
}
