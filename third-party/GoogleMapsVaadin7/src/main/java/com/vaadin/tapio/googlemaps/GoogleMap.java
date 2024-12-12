package com.vaadin.tapio.googlemaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.GoogleMapState;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.EdgeClickListener;
import com.vaadin.tapio.googlemaps.client.events.EdgeCompleteListener;
import com.vaadin.tapio.googlemaps.client.events.EdgeRightClickListener;
import com.vaadin.tapio.googlemaps.client.events.InfoWindowClosedListener;
import com.vaadin.tapio.googlemaps.client.events.MapClickListener;
import com.vaadin.tapio.googlemaps.client.events.MapDblClickListener;
import com.vaadin.tapio.googlemaps.client.events.MapMouseMoveListener;
import com.vaadin.tapio.googlemaps.client.events.MapMouseOverListener;
import com.vaadin.tapio.googlemaps.client.events.MapMoveListener;
import com.vaadin.tapio.googlemaps.client.events.MapRightClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerDblClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerRightClickListener;
import com.vaadin.tapio.googlemaps.client.events.PolygonClickListener;
import com.vaadin.tapio.googlemaps.client.events.PolygonCompleteListener;
import com.vaadin.tapio.googlemaps.client.events.PolygonDblClickListener;
import com.vaadin.tapio.googlemaps.client.events.PolygonRightClickListener;
import com.vaadin.tapio.googlemaps.client.events.PolylineClickListener;
import com.vaadin.tapio.googlemaps.client.events.PolylineCompleteListener;
import com.vaadin.tapio.googlemaps.client.events.PolylineDblClickListener;
import com.vaadin.tapio.googlemaps.client.events.PolylineRightClickListener;
import com.vaadin.tapio.googlemaps.client.layers.GoogleMapKmlLayer;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.tapio.googlemaps.client.rpcs.EdgeClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.EdgeCompletedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.EdgeRightClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.InfoWindowClosedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MapClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MapDblClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MapMouseMovedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MapMouseOverRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MapMovedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MapRightClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MapTypeChangedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MarkerClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MarkerDblClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MarkerDraggedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.MarkerRightClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolygonClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolygonCompletedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolygonDblClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolygonRightClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolylineClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolylineCompletedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolylineDblClickedRpc;
import com.vaadin.tapio.googlemaps.client.rpcs.PolylineRightClickedRpc;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * The class representing Google Maps.
 */
public class GoogleMap extends AbstractComponentContainer {

    /**
     * Base map types supported by Google Maps.
     */
    public enum MapType {
        Hybrid, Roadmap, Satellite, Terrain
    }
    
    private final MarkerClickedRpc markerClickedRpc = new MarkerClickedRpc() {
        @Override
        public void markerClicked(long markerId) {

            GoogleMapMarker marker = getState().markers.get(markerId);
            for (MarkerClickListener listener : markerClickListeners) {
                listener.markerClicked(marker);
            }
        }
    };
    
    private final MarkerDblClickedRpc markerDblClickedRpc = new MarkerDblClickedRpc() {
        @Override
        public void markerDblClicked(long markerId) {

            GoogleMapMarker marker = getState().markers.get(markerId);
            for (MarkerDblClickListener listener : markerDblClickListeners) {
                listener.markerDblClicked(marker);
            }
        }
    };
    
    private final MarkerRightClickedRpc markerRightClickedRpc = new MarkerRightClickedRpc() {
        @Override
        public void markerRightClicked(long markerId) {

            GoogleMapMarker marker = getState().markers.get(markerId);
            for (MarkerRightClickListener listener : markerRightClickListeners) {
                listener.markerRightClicked(marker);
            }
        }
    };

    private final MarkerDraggedRpc markerDraggedRpc = new MarkerDraggedRpc() {
        @Override
        public void markerDragged(long markerId, LatLon newPosition) {
            GoogleMapMarker marker = getState().markers.get(markerId);
            LatLon oldPosition = marker.getPosition();
            marker.setPosition(newPosition);
            for (MarkerDragListener listener : markerDragListeners) {
                listener.markerDragged(marker, oldPosition);
            }
        }
    };
        
    private final PolygonClickedRpc polygonClickedRpc = new PolygonClickedRpc() {
        @Override
        public void polygonClicked(GoogleMapPolygon gmPolygon) {
            
           GoogleMapPolygon polygon = getState().polygons.get(gmPolygon.getId());
           polygon.setEditable(gmPolygon.isEditable());
            
            for (PolygonClickListener listener : polygonClickListeners) {
                listener.polygonClicked(polygon);
            }            
        }
    };
    
    private final PolygonDblClickedRpc polygonDblClickedRpc = new PolygonDblClickedRpc() {
        @Override
        public void polygonDblClicked(long polygonId) {
            
            GoogleMapPolygon polygon = getState().polygons.get(polygonId);
            for (PolygonDblClickListener listener : polygonDblClickListeners) {
                listener.polygonDblClicked(polygon);
            } 
        }
    };
    
    private final PolygonRightClickedRpc polygonRightClickedRpc = new PolygonRightClickedRpc() {
        @Override
        public void polygonRightClicked(long polygonId) {
            
            GoogleMapPolygon polygon = getState().polygons.get(polygonId);
            for (PolygonRightClickListener listener : polygonRightClickListeners) {
                listener.polygonRightClicked(polygon);
            } 
        }
    };
    
    private final PolygonCompletedRpc polygonCompletedRpc = new PolygonCompletedRpc() {

        @Override
        public void polygonComplete(GoogleMapPolygon gmPolygon) {
            for (PolygonCompleteListener listener : polygonCompleteListeners) {
                listener.polygonCompleted(gmPolygon);
            }
        }
    };
    
    private final PolylineCompletedRpc polylineCompletedRpc = new PolylineCompletedRpc() {

        @Override
        public void polylineCompleted(GoogleMapPolyline gmPolyline) {
            
            for (PolylineCompleteListener listener : polylineCompleteListeners)
                listener.polylineCompleted(gmPolyline);
        }
    };
    
    private final PolylineClickedRpc polylineClickedRpc = new PolylineClickedRpc() {
        @Override
        public void polylineClicked(long polylineId) {

            GoogleMapPolyline polyline = getState().polylines.get(polylineId);
            for (PolylineClickListener listener : polylineClickListeners) {
                listener.polylineClicked(polyline);
            }
        }
    };
    
    private final PolylineDblClickedRpc polylineDblClickedRpc = new PolylineDblClickedRpc() {
        @Override
        public void polylineDblClicked(long polylineId) {

            GoogleMapPolyline polyline = getState().polylines.get(polylineId);
            for (PolylineDblClickListener listener : polylineDblClickListeners) {
                listener.polylineDblClicked(polyline);
            }
        }
    };
    
    private final PolylineRightClickedRpc polylineRightClickedRpc = new PolylineRightClickedRpc() {
        @Override
        public void polylineRightClicked(long polylineId) {

            GoogleMapPolyline polyline = getState().polylines.get(polylineId);
            for (PolylineRightClickListener listener : polylineRightClickListeners) {
                listener.polylineRightClicked(polyline);
            }
        }
    };
    
    private final EdgeClickedRpc edgeClickedRpc = new EdgeClickedRpc() {

        @Override
        public void edgeClicked(GoogleMapPolyline clickedEdge) {
            if (edgeIDs.containsKey(clickedEdge.getId())) {
                GoogleMapPolyline edge = edgeIDs.get(clickedEdge.getId());
                edge.setEditable(clickedEdge.isEditable());

                for (EdgeClickListener listener : edgeClickListeners) {
                    listener.edgeClicked(edge);
                }
            }
        }
    };
    
    private final EdgeCompletedRpc edgeCompletedRpc = new EdgeCompletedRpc() {

        @Override
        public void edgeCompleted(GoogleMapPolyline edgeComplete) {
            if (edgeIDs.containsKey(edgeComplete.getId())) {
                GoogleMapPolyline edge = edgeIDs.get(edgeComplete.getId());
                edge.getCoordinates().clear();
                
                List<GoogleMapMarker> nodes = getState().edges.get(edge);
                nodes.get(0).setPosition(edgeComplete.getCoordinates().get(0));
                nodes.get(1).setPosition(edgeComplete.getCoordinates()
                    .get(edgeComplete.getCoordinates().size() - 1));
                
                for (LatLon coordinate : edgeComplete.getCoordinates()) {
                    edge.getCoordinates().add(coordinate);
                }                
                for (EdgeCompleteListener listener : edgeCompleteListeners) {
                    listener.edgeCompleted(edge);
                }
            }
        }
    };
    
    private final EdgeRightClickedRpc edgeRightClickedRpc = new EdgeRightClickedRpc() {

        @Override
        public void edgeRightClicked(GoogleMapPolyline clickedEdge) {
            if (edgeIDs.containsKey(clickedEdge.getId())) {
                GoogleMapPolyline edge = edgeIDs.get(clickedEdge.getId());

                for (EdgeRightClickListener listener : edgeRightClickListeners) {
                    listener.edgeRightClicked(edge);
                }
            }
        }
    };

    private final MapMovedRpc mapMovedRpc = new MapMovedRpc() {
        @Override
        public void mapMoved(int zoomLevel, LatLon center, LatLon boundsNE,
            LatLon boundsSW) {
            getState().zoom = zoomLevel;
            getState().center = center;
            fitToBounds(null, null);

            for (MapMoveListener listener : mapMoveListeners) {
                listener.mapMoved(zoomLevel, center, boundsNE, boundsSW);
            }

        }
    };

    private final MapClickedRpc mapClickedRpc = new MapClickedRpc() {
        @Override
        public void mapClicked(LatLon position) {
            for (MapClickListener listener : mapClickListeners) {
                listener.mapClicked(position);
            }
        }
    };
    
    private final MapDblClickedRpc mapDblClickedRpc = new MapDblClickedRpc() {
        @Override
        public void mapDblClicked(LatLon position) {
            for (MapDblClickListener listener : mapDblClickListeners) {
                listener.mapDblClicked(position);
            }
        }
    };
    
    private final MapRightClickedRpc mapRightClickedRpc = new MapRightClickedRpc() {
        @Override
        public void mapRightClicked(LatLon position) {
            for (MapRightClickListener listener : mapRightClickListeners) {
                listener.mapRightClicked(position);
            }
        }
    };
    
    
    private final InfoWindowClosedRpc infoWindowClosedRpc = new InfoWindowClosedRpc() {

        @Override
        public void infoWindowClosed(long windowId) {
            GoogleMapInfoWindow window = getState().infoWindows.get(windowId);
            for (InfoWindowClosedListener listener : infoWindowClosedListeners) {
                listener.infoWindowClosed(window);
            }
            getState().infoWindows.remove(windowId);
        }
    };

    private final MapTypeChangedRpc mapTypeChangedRpc = new MapTypeChangedRpc() {
        @Override
        public void mapTypeChanged(String mapTypeId) {
            MapType mapType = MapType
                .valueOf(StringUtils.capitalize(mapTypeId));
            setMapType(mapType);
        }
    };
    
    private final MapMouseMovedRpc mapMouseMovedRpc = new MapMouseMovedRpc() {

        @Override
        public void mapMouseMoved(LatLon position) {
            for (MapMouseMoveListener listener : mapMouseMoveListeners) {
                listener.mapMouseMoved(position);
            }
        }
    };
    
    private final MapMouseOverRpc mapMouseOverRpc = new MapMouseOverRpc() {
        
        @Override
        public void mapMouseOver(LatLon position) {
            for (MapMouseOverListener listener : mapMouseOverListeners) {
                listener.mapMouseOver(position);
            }
        }
    };

    private final List<MarkerClickListener> markerClickListeners = new ArrayList<>();
    
    private final List<MarkerDblClickListener> markerDblClickListeners = new ArrayList<>();
    
    private final List<MarkerRightClickListener> markerRightClickListeners = new ArrayList<>();
        
    private final List<PolygonClickListener> polygonClickListeners = new ArrayList<>();
    
    private final List<PolygonDblClickListener> polygonDblClickListeners = new ArrayList<>();
    
    private final List<PolygonRightClickListener> polygonRightClickListeners = new ArrayList<>();
    
    private final List<PolygonCompleteListener> polygonCompleteListeners = new ArrayList<>();
    
    private final List<PolylineCompleteListener> polylineCompleteListeners = new ArrayList<>();
    
    private final List<PolylineClickListener> polylineClickListeners = new ArrayList<>();
    
    private final List<PolylineDblClickListener> polylineDblClickListeners = new ArrayList<>();
    
    private final List<PolylineRightClickListener> polylineRightClickListeners = new ArrayList<>();
    
    private final List<EdgeClickListener> edgeClickListeners = new ArrayList();
    
    private final List<EdgeCompleteListener> edgeCompleteListeners = new ArrayList();
    
    private final List<EdgeRightClickListener> edgeRightClickListeners = new ArrayList<>();

    private final List<MapMoveListener> mapMoveListeners = new ArrayList<>();

    private final List<MapClickListener> mapClickListeners = new ArrayList<>();
    
    private final List<MapDblClickListener> mapDblClickListeners = new ArrayList<>();
    
    private final List<MapRightClickListener> mapRightClickListeners = new ArrayList<>();
    
    private final List<MarkerDragListener> markerDragListeners = new ArrayList<>();
    
    private final List<MapMouseMoveListener> mapMouseMoveListeners = new ArrayList<>();
    
    private final List<MapMouseOverListener> mapMouseOverListeners = new ArrayList<>();

    private final List<InfoWindowClosedListener> infoWindowClosedListeners = new ArrayList<>();

    private final Map<GoogleMapInfoWindow, Component> infoWindowContents = new HashMap<>();
    
    private final Map<Long, GoogleMapPolyline> edgeIDs = new HashMap<>();

    /**
     * The layout that actually contains the contents of Info Windows (if Vaadin components are used).
     * Should never be visible itself.
     */
    private final CssLayout infoWindowContentLayout = new CssLayout();

    /**
     * Initiates a new GoogleMap object with default settings from the
     * {@link GoogleMapState state object}.
     *
     * @param apiKey   The Maps API key from Google. Not required when developing in
     *                 localhost or when using a client id. Use null or empty string
     *                 to disable.
     * @param clientId Google Maps API for Work client ID. Use this instead of API
     *                 key if available. Use null or empty string to disable.
     * @param language The language to use with maps. See
     *                 https://developers.google.com/maps/faq#languagesupport for the
     *                 list of the supported languages. Use null or empty string to
     *                 disable.
     */
    public GoogleMap(String apiKey, String clientId, String language) {
        infoWindowContentLayout
            .addStyleName(
                "googlemaps-infowindow-components-layout should-be-invisible");

        if (apiKey != null && !apiKey.isEmpty()) {
            getState().apiKey = apiKey;
        }
        if (clientId != null && !clientId.isEmpty()) {
            getState().clientId = clientId;
        }

        if (language != null && !language.isEmpty()) {
            getState().language = language;
        }

        registerRpc(markerClickedRpc);
        registerRpc(markerDblClickedRpc);
        registerRpc(markerRightClickedRpc);
        registerRpc(polygonClickedRpc);
        registerRpc(polygonDblClickedRpc);
        registerRpc(polygonRightClickedRpc);
        registerRpc(polygonCompletedRpc);
        registerRpc(polylineCompletedRpc);
        registerRpc(polylineClickedRpc);
        registerRpc(polylineDblClickedRpc);
        registerRpc(polylineRightClickedRpc);
        registerRpc(edgeClickedRpc);
        registerRpc(edgeCompletedRpc);
        registerRpc(edgeRightClickedRpc);
        registerRpc(mapMovedRpc);
        registerRpc(mapClickedRpc);
        registerRpc(mapDblClickedRpc);
        registerRpc(mapRightClickedRpc);
        registerRpc(mapMouseMovedRpc);
        registerRpc(mapMouseOverRpc);
        registerRpc(markerDraggedRpc);
        registerRpc(infoWindowClosedRpc);
        registerRpc(mapTypeChangedRpc);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.vaadin.ui.AbstractComponent#getState()
     */
    @Override
    protected GoogleMapState getState() {
        return (GoogleMapState) super.getState();
    }

    /**
     * Sets the center of the map to the given coordinates.
     *
     * @param center The new coordinates of the center.
     */
    public void setCenter(LatLon center) {
        getState().center = center;
    }

    /**
     * Returns the current position of the center of the map.
     *
     * @return Coordinates of the center.
     */
    public LatLon getCenter() {
        return getState().center;
    }

    /**
     * Zooms the map to the given value.
     *
     * @param zoom New amount of the zoom.
     */
    public void setZoom(int zoom) {
        getState().zoom = zoom;
    }

    /**
     * Returns the current zoom of the map.
     *
     * @return Current value of the zoom.
     */
    public int getZoom() {
        return getState().zoom;
    }

    /**
     * Adds a new marker to the map.
     *
     * @param caption   Caption of the marker shown when the marker is hovered.
     * @param position  Coordinates of the marker on the map.
     * @param draggable Set true to enable dragging of the marker.
     * @param iconUrl   The url of the icon of the marker.
     * @return GoogleMapMarker object created with the given settings.
     */
    public GoogleMapMarker addMarker(String caption, LatLon position,
        boolean draggable, String iconUrl) {
        GoogleMapMarker marker = new GoogleMapMarker(caption, position,
            draggable, iconUrl);
        getState().markers.put(marker.getId(), marker);
        return marker;
    }
    
    /**
     * Adds a new marker to the map.
     *
     * @param caption   Caption of the marker shown when the marker is hovered.
     * @param position  Coordinates of the marker on the map.
     * @param draggable Set true to enable dragging of the marker.
     * @return GoogleMapMarker object created with the given settings.
     */
    public GoogleMapMarker addMarker(String caption, LatLon position,
        boolean draggable) {
        GoogleMapMarker marker = new GoogleMapMarker(caption, position,
            draggable);
        getState().markers.put(marker.getId(), marker);
        return marker;
    }

    /**
     * Adds a marker to the map.
     *
     * @param marker The marker to add.
     */
    public void addMarker(GoogleMapMarker marker) {
        getState().markers.put(marker.getId(), marker);
    }

    /**
     * Removes a marker from the map.
     *
     * @param marker The marker to remove.
     */
    public void removeMarker(GoogleMapMarker marker) {
        getState().markers.remove(marker.getId());
    }

    /**
     * Removes all the markers from the map.
     */
    public void clearMarkers() {
        getState().markers = new HashMap<>();
    }

    /**
     * Checks if a marker has been added to the map.
     *
     * @param marker The marker to check.
     * @return true, if the marker has been added to the map.
     */
    public boolean hasMarker(GoogleMapMarker marker) {
        return getState().markers.containsKey(marker.getId());
    }

    /**
     * Returns the markers that have been added to he map.
     *
     * @return Set of the markers.
     */
    public Collection<GoogleMapMarker> getMarkers() {
        return getState().markers.values();
    }

    /**
     * Adds a MarkerClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMarkerClickListener(MarkerClickListener listener) {
        markerClickListeners.add(listener);
    }
    
    /**
     * Returns the marker click listeners
     * 
     * @return Set of the marker click listeners
     */
    public List<MarkerClickListener> getMarkerClickListeners() {
        return markerClickListeners;
    }

    /**
     * Removes a MarkerClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeMarkerClickListener(MarkerClickListener listener) {
        markerClickListeners.remove(listener);
    }

    /**
     * Adds a MarkerDragListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMarkerDragListener(MarkerDragListener listener) {
        markerDragListeners.add(listener);
    }

    /**
     * Removes a MarkerDragListenr from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeMarkerDragListener(MarkerDragListener listener) {
        markerDragListeners.remove(listener);
    }
    
    /**
     * Adds a MarkerDblClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMarkerDblClickListener(MarkerDblClickListener listener) {
        markerDblClickListeners.add(listener);
    }

    /**
     * Removes a MarkerDblClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeDblClickListener(MarkerDblClickListener listener) {
        markerDblClickListeners.remove(listener);
    }
    
    /**
     * Adds a MarkerRightClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMarkerRightClickListener(MarkerRightClickListener listener) {
        markerRightClickListeners.add(listener);
    }

    /**
     * Removes a MarkerRightClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeMarkerRightClickListener(MarkerRightClickListener listener) {
        markerRightClickListeners.remove(listener);
    }
    
    /**
     * Adds a PolygonClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolygonClickListener(PolygonClickListener listener) {
        polygonClickListeners.add(listener);
    }
    
    /**
     * Removes a PolygonClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolygonClickListener(PolygonClickListener listener) {
        polygonClickListeners.remove(listener);
    }
    
    /**
     * Adds a PolygonDblClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolygonDblClickListener(PolygonDblClickListener listener) {
        polygonDblClickListeners.add(listener);
    }
    
    /**
     * Removes a PolygonDblClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolygonDblClickListener(PolygonDblClickListener listener) {
        polygonDblClickListeners.remove(listener);
    }
    
    /**
     * Adds a PolygonRightClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolygonRightClickListener(PolygonRightClickListener listener) {
        polygonRightClickListeners.add(listener);
    }
    
    /**
     * Removes a PolygonRightClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolygonRightClickListener(PolygonRightClickListener listener) {
        polygonRightClickListeners.remove(listener);
    }
    
    /**
     * Adds a PolygonCompleteListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolygonCompleteListener(PolygonCompleteListener listener) {
        polygonCompleteListeners.add(listener);
    }
    
    /**
     * Removes a PolygonCompleteListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolygonCompleteListener(PolygonCompleteListener listener) {
        polygonCompleteListeners.remove(listener);
    }
    
    /**
     * Adds a PolylineCompleteListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolylineCompleteListener(PolylineCompleteListener listener) {
        polylineCompleteListeners.add(listener);
    }
    
    /**
     * Removes a PolylineCompleteListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolylineClickListener(PolylineCompleteListener listener) {
        polylineCompleteListeners.remove(listener);
    }
    
    /**
     * Adds a PolylineClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolylineClickListener(PolylineClickListener listener) {
        polylineClickListeners.add(listener);
    }

    /**
     * Removes a PolylineClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolylineClickListener(PolylineClickListener listener) {
        polylineClickListeners.remove(listener);
    }
    
    /**
     * Adds a PolylineDblClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolylineDblClickListener(PolylineDblClickListener listener) {
        polylineDblClickListeners.add(listener);
    }

    /**
     * Removes a PolylineDblClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolylineDblClickListener(PolylineDblClickListener listener) {
        polylineDblClickListeners.remove(listener);
    }
    
    /**
     * Adds a PolylineRightClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addPolylineRightClickListener(PolylineRightClickListener listener) {
        polylineRightClickListeners.add(listener);
    }

    /**
     * Removes a PolylineRightClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removePolylineRightClickListener(PolylineRightClickListener listener) {
        polylineRightClickListeners.remove(listener);
    }
    
    /**
     * Adds a EdgeClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addEdgeClickListener(EdgeClickListener listener) {
        edgeClickListeners.add(listener);
    }

    /**
     * Removes a EdgeClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeEdgeClickListener(EdgeClickListener listener) {
        edgeClickListeners.remove(listener);
    }
    
    /**
     * Adds a EdgeCompleteListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addEdgeCompleteListener(EdgeCompleteListener listener) {
        edgeCompleteListeners.add(listener);
    }

    /**
     * Removes a EdgeCompleteListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeEdgeCompleteListener(EdgeCompleteListener listener) {
        edgeCompleteListeners.remove(listener);
    }
    
    /**
     * Adds a EdgeRightClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addEdgeRightClickListener(EdgeRightClickListener listener) {
        edgeRightClickListeners.add(listener);
    }

    /**
     * Removes a EdgeRightClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeEdgeRightClickListener(EdgeRightClickListener listener) {
        edgeRightClickListeners.remove(listener);
    }

    /**
     * Adds a MapMoveListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMapMoveListener(MapMoveListener listener) {
        mapMoveListeners.add(listener);
    }

    /**
     * Removes a MapMoveListener from the map.
     *
     * @param listener The listener to add.
     */
    public void removeMapMoveListener(MapMoveListener listener) {
        mapMoveListeners.remove(listener);
    }

    /**
     * Adds a MapClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMapClickListener(MapClickListener listener) {
        mapClickListeners.add(listener);
    }

    /**
     * Removes a MapClickListener from the map.
     *
     * @param listener The listener to add.
     */
    public void removeMapClickListener(MapClickListener listener) {
        mapClickListeners.remove(listener);
    }
    
    /**
     * Adds a MapDblClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMapDblClickListener(MapDblClickListener listener) {
        mapDblClickListeners.add(listener);
    }

    /**
     * Removes a MapDblClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeMapClickListener(MapDblClickListener listener) {
        mapDblClickListeners.remove(listener);
    }
    
    /**
     * Adds a MapRightClickListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMapRightClickListener(MapRightClickListener listener) {
        mapRightClickListeners.add(listener);
    }

    /**
     * Removes a MapRightClickListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeMapRightClickListener(MapRightClickListener listener) {
        mapRightClickListeners.remove(listener);
    }
    
    /**
     * Adds a MapMouseMoveListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMapMouseMoveListener(MapMouseMoveListener listener) {
        mapMouseMoveListeners.add(listener);
    }
    
    /**
     * Removes a MapMouseMoveListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeMapMouseMoveListener(MapMouseMoveListener listener) {
        mapMouseMoveListeners.remove(listener);
    }
    
    /**
     * Adds a MapMouseOverListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addMapMouseOverListener(MapMouseOverListener listener) {
        mapMouseOverListeners.add(listener);
    }
    
    /**
     * Removes a MapMouseOverListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeMapMouseOverListener(MapMouseOverListener listener) {
        mapMouseOverListeners.remove(listener);
    }

    /**
     * Adds an InfoWindowClosedListener to the map.
     *
     * @param listener The listener to add.
     */
    public void addInfoWindowClosedListener(InfoWindowClosedListener listener) {
        infoWindowClosedListeners.add(listener);
    }

    /**
     * Removes an InfoWindowClosedListener from the map.
     *
     * @param listener The listener to remove.
     */
    public void removeInfoWindowClosedListener(
        InfoWindowClosedListener listener) {
        infoWindowClosedListeners.remove(listener);
    }

    /**
     * Checks if limiting of the center bounds is enabled.
     *
     * @return true, if enabled
     */
    public boolean isCenterBoundLimitsEnabled() {
        return getState().limitCenterBounds;
    }

    /**
     * Enables/disables limiting of the center bounds.
     *
     * @param enable Set true to enable the limiting.
     */
    public void setCenterBoundLimitsEnabled(boolean enable) {
        getState().limitCenterBounds = enable;
    }

    /**
     * Sets the limits of the bounds of the center to given values.
     *
     * @param limitNE The coordinates of the northeast limit.
     * @param limitSW The coordinates of the southwest limit.
     */
    public void setCenterBoundLimits(LatLon limitNE, LatLon limitSW) {
        getState().centerNELimit = limitNE;
        getState().centerSWLimit = limitSW;
        getState().limitCenterBounds = true;
    }

    /**
     * Adds a polygon overlay to the map.
     *
     * @param polygon The GoogleMapPolygon to add.
     */
    public void addPolygonOverlay(GoogleMapPolygon polygon) {
        getState().polygons.put(polygon.getId(), polygon);
    }

    /**
     * Removes a polygon overlay from the map.
     *
     * @param polygon The GoogleMapPolygon to remove.
     */
    public void removePolygonOverlay(GoogleMapPolygon polygon) {
        getState().polygons.remove(polygon.getId());
    }

    /**
     * Adds a polyline to the map.
     *
     * @param caption The caption for this polyline
     * @return The newly created polyline
     */
    public GoogleMapPolyline addPolyline(String caption) {
        GoogleMapPolyline polyline = new GoogleMapPolyline();
        getState().polylines.put(polyline.getId(), polyline);
        return polyline;
    }
    
    /**
     * Adds a polyline to the map.
     *
     * @param polyline The GoogleMapPolyline to add
     */
    public void addPolyline(GoogleMapPolyline polyline) {
        getState().polylines.put(polyline.getId(), polyline);
    }
    

    /**
     * Removes a polyline from the map.
     *
     * @param polyline The GoogleMapPolyline to remove.
     */
    public void removePolyline(GoogleMapPolyline polyline) {
        getState().polylines.remove(polyline.getId());
    }

    /**
     * Adds a KML layer to the map.
     *
     * @param kmlLayer The KML layer to add.
     */
    public void addKmlLayer(GoogleMapKmlLayer kmlLayer) {
        getState().kmlLayers.add(kmlLayer);
    }

    /**
     * Removes a KML layer from the map.
     *
     * @param kmlLayer The KML layer to remove.
     */
    public void removeKmlLayer(GoogleMapKmlLayer kmlLayer) {
        getState().kmlLayers.remove(kmlLayer);
    }

    /**
     * Sets the type of the base map.
     *
     * @param type The new MapType to use.
     */
    public void setMapType(MapType type) {
        getState().mapTypeId = type.name();
    }

    /**
     * Returns the current type of the base map.
     *
     * @return The current MapType.
     */
    public MapType getMapType() {
        return MapType.valueOf(getState().mapTypeId);
    }

    /**
     * Checks if the map is currently draggable.
     *
     * @return true, if the map draggable.
     */
    public boolean isDraggable() {
        return getState().draggable;
    }

    /**
     * Enables/disables dragging of the map.
     *
     * @param draggable Set to true to enable dragging.
     */
    public void setDraggable(boolean draggable) {
        getState().draggable = draggable;
    }

    /**
     * Checks if the keyboard shortcuts are enabled.
     *
     * @return true, if the shortcuts are enabled.
     */
    public boolean areKeyboardShortcutsEnabled() {
        return getState().keyboardShortcutsEnabled;
    }

    /**
     * Enables/disables the keyboard shortcuts.
     *
     * @param enabled Set true to enable keyboard shortcuts.
     */
    public void setKeyboardShortcutsEnabled(boolean enabled) {
        getState().keyboardShortcutsEnabled = enabled;
    }

    /**
     * Checks if the scroll wheel is enabled.
     *
     * @return true, if the scroll wheel is enabled
     */
    public boolean isScrollWheelEnabled() {
        return getState().scrollWheelEnabled;
    }

    /**
     * Enables/disables the scroll wheel.
     *
     * @param enabled Set true to enable scroll wheel.
     */
    public void setScrollWheelEnabled(boolean enabled) {
        getState().scrollWheelEnabled = enabled;
    }

    /**
     * Returns the currently enabled map controls.
     *
     * @return Currently enabled map controls.
     */
    public Set<GoogleMapControl> getControls() {
        return getState().controls;
    }

    /**
     * Sets the controls of the map.
     *
     * @param controls The new controls to use.
     */
    public void setControls(Set<GoogleMapControl> controls) {
        getState().controls = controls;
    }

    /**
     * Enables the given control on the map. Does nothing if the control is
     * already enabled.
     *
     * @param control The control to enable.
     */
    public void addControl(GoogleMapControl control) {
        getState().controls.add(control);
    }

    /**
     * Removes the control from the map. Does nothing if the control isn't
     * enabled.
     *
     * @param control The control to remove.
     */
    public void removeControl(GoogleMapControl control) {
        getState().controls.remove(control);
    }

    /**
     * Enables/disables limiting of the bounds of the visible area.
     *
     * @param enabled Set true to enable the limiting.
     */
    public void setVisibleAreaBoundLimitsEnabled(boolean enabled) {
        getState().limitVisibleAreaBounds = enabled;

    }

    /**
     * Checks if limiting of the bounds of the visible area is enabled.
     *
     * @return true if enabled
     */
    public boolean isVisibleAreaBoundLimitsEnabled() {
        return getState().limitVisibleAreaBounds;
    }

    /**
     * Sets the limits of the bounds of the visible area to the given values.
     * NOTE: Using the feature does not affect zooming, consider using
     * {@link #setMinZoom(int)} too.
     *
     * @param limitNE The coordinates of the northeast limit.
     * @param limitSW The coordinates of the southwest limit.
     */
    public void setVisibleAreaBoundLimits(LatLon limitNE, LatLon limitSW) {
        getState().visibleAreaNELimit = limitNE;
        getState().visibleAreaSWLimit = limitSW;
        getState().limitVisibleAreaBounds = true;
    }

    /**
     * Sets the maximum allowed amount of zoom (default 21.0).
     *
     * @param maxZoom The maximum amount for zoom.
     */
    public void setMaxZoom(int maxZoom) {
        getState().maxZoom = maxZoom;
    }

    /**
     * Returns the current maximum amount of zoom.
     *
     * @return maximum amount of zoom
     */
    public int getMaxZoom() {
        return getState().maxZoom;
    }

    /**
     * Sets the minimum allowed amount of zoom (default 0.0).
     *
     * @param minZoom The minimum amount for zoom.
     */
    public void setMinZoom(int minZoom) {
        getState().minZoom = minZoom;
    }

    /**
     * Returns the current minimum amount of zoom.
     *
     * @return minimum amount of zoom
     */
    public int getMinZoom() {
        return getState().minZoom;
    }

    /**
     * Opens an info window.
     *
     * @param infoWindow The window to open.
     */
    public void openInfoWindow(GoogleMapInfoWindow infoWindow) {
        getState().infoWindows.put(infoWindow.getId(), infoWindow);
    }

    /**
     * Closes an info window.
     *
     * @param infoWindow The window to close.
     */
    public void closeInfoWindow(GoogleMapInfoWindow infoWindow) {
        getState().infoWindows.remove(infoWindow.getId());
    }

    /**
     * Checks if an info window is open.
     *
     * @param infoWindow The window to check.
     * @return true, if the window is open.
     */
    public boolean isInfoWindowOpen(GoogleMapInfoWindow infoWindow) {
        return getState().infoWindows.containsKey(infoWindow.getId());
    }

    /**
     * Tries to fit the visible area of the map inside given boundaries by
     * modifying zoom and/or center.
     *
     * @param boundsNE The northeast boundaries.
     * @param boundsSW The southwest boundaries.
     */
    public void fitToBounds(LatLon boundsNE, LatLon boundsSW) {
        getState().fitToBoundsNE = boundsNE;
        getState().fitToBoundsSW = boundsSW;
    }

    /**
     * Check if a traffic layer is visible
     *
     * @return true, if traffic layer is visible
     */
    public boolean isTrafficLayerVisible() {
        return getState().trafficLayerVisible;
    }

    /**
     * Set a traffic layer visibility
     *
     * @param visible
     */
    public void setTrafficLayerVisible(boolean visible) {
        getState().trafficLayerVisible = visible;
    }

    /**
     * Set a custom url for API. For example Chinese API would be
     * "maps.google.cn".
     *
     * @param url the url to use WITHOUT protocol (http/https)
     */
    public void setApiUrl(String url) {
        getState().apiUrl = url;
    }

    @Override
    public void replaceComponent(Component oldComponent,
        Component newComponent) {
        for (GoogleMapInfoWindow window : infoWindowContents.keySet()) {
            if (infoWindowContents.get(window).equals(oldComponent)) {
                setInfoWindowContents(window, newComponent);
                super.removeComponent(oldComponent);
                break;
            }
        }
    }

    @Override
    public int getComponentCount() {
        return infoWindowContents.size();
    }

    @Override
    public Iterator<Component> iterator() {
        return infoWindowContents.values().iterator();
    }

    /**
     * Sets the contents of an info window to a single Vaadin component which may,
     * of course, be a layout.
     *
     * @param window  the info window which contents should be modified
     * @param content the contents for the info window
     */
    public void setInfoWindowContents(GoogleMapInfoWindow window,
        Component content) {
        super.addComponent(content);
        infoWindowContents.put(window, content);
        String contentIdentifier = "content-for-infowindow-" + window.getId();
        content.addStyleName(contentIdentifier);
        window.setContent("Loading...");
        getState().infoWindowContentIdentifiers
            .put(window.getId(), contentIdentifier);
    }
    
    public boolean isDisableDoubleClickZoom() {
        return getState().disableDoubleClickZoom;
    }
    
    public void setDisableDoubleClickZoom(boolean disableDoubleClickZoom) {
        getState().disableDoubleClickZoom = disableDoubleClickZoom;
    }
    
    public void addEdge(GoogleMapPolyline edge, GoogleMapMarker sourceNode, GoogleMapMarker targetNode) {
        if (!getState().edges.containsKey(edge)) {
            List<GoogleMapMarker> nodes = new ArrayList();
            nodes.add(sourceNode);
            nodes.add(targetNode);
            
            edgeIDs.put(edge.getId(), edge);
            getState().edges.put(edge, nodes);
        }
    }
    
    public void removeEdge(GoogleMapPolyline edge) {
        edgeIDs.remove(edge.getId());
        getState().edges.remove(edge);
    }
    
    public void removeEdges() {
        edgeIDs.clear();
        getState().edges.clear();
    }
    
    public boolean getMeasureDistance() {
        return getState().measureDistance;
    }
    
    public void setMeasureDistance(boolean measureDistance) {
        getState().measureDistance = measureDistance;
    }
    
    public boolean getMesaureEdgeDistance() {
        return getState().mesaureEdgeDistance;
    }
    
    public void setMeasureEdgeDistance(boolean measureEdgeDistance) {
        getState().mesaureEdgeDistance = measureEdgeDistance;
    }
    
    public boolean getShowMarkerLabels() {
        return getState().showMarkerLabels;
    } 
    
    public void showMarkerLabels(boolean showMarkerLabels) {
        getState().showMarkerLabels = showMarkerLabels;
    }
    
    public boolean getShowEdgeLabels() {
        return getState().showEdgeLabels;
    } 
    
    public void showEdgeLabels(boolean showEdgeLabels) {
        getState().showEdgeLabels = showEdgeLabels;
    }
}
