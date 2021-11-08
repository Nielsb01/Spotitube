package nl.han.ica.oose.dea.niels.resourcelayer;

import nl.han.ica.oose.dea.niels.resourcelayer.dtos.PlaylistDTO;
import nl.han.ica.oose.dea.niels.resourcelayer.dtos.TrackDTO;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.IPlaylistService;
import nl.han.ica.oose.dea.niels.servicelayer.interfaces.ITrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {
    private IPlaylistService playlistService;
    private ITrackService trackService;

    @Inject
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        return Response.status(200).entity(playlistService.getAll(token)).build();
    }

    @DELETE
    @Path("/{playlistId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylists(@PathParam("playlistId") int playlistId, @QueryParam("token") String token){
        return Response.status(200).entity(playlistService.deletePlaylist(playlistId, token)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token) {
        return Response.status(201).entity(playlistService.addPlaylist(playlistDTO, token)).build();
    }

    @PUT
    @Path("/{playlistId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(PlaylistDTO playlistDTO, @PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
        return Response.status(200).entity(playlistService.editPlaylist(playlistDTO, playlistId, token)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks")
    public Response getTracksOfPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token) {
        return Response.status(200).entity(trackService.getTracksInPlaylist(playlistId, token)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        return Response.status(200).entity(trackService.deleteTrackFromPlaylist(playlistId, trackId, token)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks")
    public Response addTrackToPlaylist(@PathParam("playlistId") int playlistId, TrackDTO trackDTO, @QueryParam("token") String token) {
        return Response.status(201).entity(trackService.addTrackToPlaylist(playlistId, trackDTO, token)).build();
    }
}
