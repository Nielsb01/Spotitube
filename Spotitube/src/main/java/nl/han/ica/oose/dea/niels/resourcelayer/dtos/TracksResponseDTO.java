package nl.han.ica.oose.dea.niels.resourcelayer.dtos;

import java.util.List;

public class TracksResponseDTO {
    private List<TrackDTO> tracks;

    public TracksResponseDTO() {
    }

    public TracksResponseDTO(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
