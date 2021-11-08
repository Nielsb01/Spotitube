package nl.han.ica.oose.dea.niels.servicelayer;

import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.IPlaylistDAO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistsResponseDTO;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.IPlaylistService;

import javax.inject.Inject;

public class PlaylistService implements IPlaylistService {
    private IPlaylistDAO playlistDAO;

    @Inject
    public void setUserDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Override
    public PlaylistsResponseDTO getAll(String token) {
        return playlistDAO.getAll(token);
    }

    @Override
    public PlaylistsResponseDTO deletePlaylist(int playlistId, String token) {
        playlistDAO.delete(playlistId, token);
        return playlistDAO.getAll(token);
    }

    @Override
    public PlaylistsResponseDTO addPlaylist(PlaylistDTO playlistDTO, String token) {
        playlistDAO.add(playlistDTO, token);
        return playlistDAO.getAll(token);
    }

    @Override
    public PlaylistsResponseDTO editPlaylist(PlaylistDTO playlistDTO, int playlistId, String token) {
        playlistDAO.edit(playlistDTO, playlistId, token);
        return playlistDAO.getAll(token);
    }
}
