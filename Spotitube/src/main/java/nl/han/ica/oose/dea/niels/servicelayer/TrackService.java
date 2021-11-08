package nl.han.ica.oose.dea.niels.servicelayer;

import nl.han.ica.oose.dea.niels.datalayer.daos.interfaces.ITrackDAO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TracksResponseDTO;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.ITrackService;

import javax.inject.Inject;

public class TrackService implements ITrackService {
    private ITrackDAO trackDAO;

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Override
    public TracksResponseDTO getAllTracksForPlaylist(int forPlaylistId, String token) {
        return trackDAO.getAllTracksForPlaylist(forPlaylistId, token);
    }

    @Override
    public TracksResponseDTO getTracksInPlaylist(int playlistId, String token) {
        return trackDAO.getAllTracksInPlaylist(playlistId, token);
    }

    @Override
    public TracksResponseDTO deleteTrackFromPlaylist(int playlistId, int trackId, String token) {
        trackDAO.deleteTrackFromPlaylist(playlistId, trackId, token);
        return trackDAO.getAllTracksInPlaylist(playlistId, token);
    }

    @Override
    public TracksResponseDTO addTrackToPlaylist(int playlistId, TrackDTO trackDTO, String token) {
        if(trackDAO.getTrack(trackDTO.getId()) != null){
            trackDAO.updateTrack(trackDTO);
            trackDAO.addTrackToPlaylist(playlistId, trackDTO.getId());
        }

        return trackDAO.getAllTracksInPlaylist(playlistId, token);
    }
}
