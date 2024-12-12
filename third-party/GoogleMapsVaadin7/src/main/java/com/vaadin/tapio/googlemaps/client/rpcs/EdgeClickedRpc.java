package com.vaadin.tapio.googlemaps.client.rpcs;

import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;

/**
 * An RPC from client to server that is called when a edge has been clicked in
 * Google Maps.
 */
public interface EdgeClickedRpc extends ServerRpc {
    void edgeClicked(GoogleMapPolyline clickedEdge);    
}
