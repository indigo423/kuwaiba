package com.vaadin.tapio.googlemaps.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.tapio.googlemaps.client.layers.GoogleMapKmlLayer;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;

/**
 * The shared state of the Google Maps. Contains also the default values.
 */
public class GoogleMapState extends AbstractComponentState {
    private static final long serialVersionUID = 646346522643L;
    
    public String apiKey = null;
    public String clientId = null;

    // defaults to the language setting of the browser
    public String language = null;
    public String mapTypeId = "Roadmap";
    public LatLon center = new LatLon(51.477811, -0.001475);
    public int zoom = 8;
    public int maxZoom = 21;
    public int minZoom = 0;

    public boolean draggable = true;
    public boolean keyboardShortcutsEnabled = true;
    public boolean scrollWheelEnabled = true;

    public Set<GoogleMapControl> controls = new HashSet<>(
        Arrays.asList(GoogleMapControl.MapType, GoogleMapControl.Pan,
            GoogleMapControl.Rotate, GoogleMapControl.Scale,
            GoogleMapControl.StreetView, GoogleMapControl.Zoom));

    public boolean limitCenterBounds = false;
    public LatLon centerSWLimit = new LatLon(0.0, 0.0);
    public LatLon centerNELimit = new LatLon(0.0, 0.0);

    public boolean limitVisibleAreaBounds = false;
    public LatLon visibleAreaSWLimit = new LatLon(0.0, 0.0);
    public LatLon visibleAreaNELimit = new LatLon(0.0, 0.0);

    public LatLon fitToBoundsNE = null;
    public LatLon fitToBoundsSW = null;

    
    
    public Set<GoogleMapKmlLayer> kmlLayers = new HashSet<GoogleMapKmlLayer>();

    public Map<Long, GoogleMapMarker> markers = new HashMap<>();
    public Map<Long, GoogleMapPolygon> polygons = new HashMap<>();
    public Map<Long, GoogleMapPolyline> polylines = new HashMap<>();
    

    public Map<Long, GoogleMapInfoWindow> infoWindows = new HashMap<>();
    public boolean trafficLayerVisible = false;

    public String apiUrl = null;

    public Map<Long, String> infoWindowContentIdentifiers = new HashMap<>();
        
    public String drawingMode = null;
    
    public boolean showMarkerLabels = false;
    public boolean showPolylineLabels = false;
    public boolean showPolygonLabels = false;
    public boolean showEdgeLabels = false;
    /**
     * Google Map source of the edge
     */
    public GoogleMapMarker markerSource = null;
    
    public boolean disableDoubleClickZoom = false;
        
    public Map<GoogleMapPolyline, List<GoogleMapMarker>> edges = new HashMap<>();
    
    public boolean measureDistance = false;
    public boolean mesaureEdgeDistance = false;
}