package nl.han.ica.oose.dea.niels.resourcelayer.dtos;

import java.util.List;

public class PlaylistsResponseDTO {
    private List<PlaylistDTO> playlists;
    private int length;

    public PlaylistsResponseDTO() {
    }

    public PlaylistsResponseDTO(List<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public List<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
