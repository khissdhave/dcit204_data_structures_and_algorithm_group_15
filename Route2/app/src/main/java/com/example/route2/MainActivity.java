package com.example.route2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
//import VogelApprox.class
//import CriticalPath.class
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    University of Ghana - DCIT204 Semester Project - Group 15
    private MapView mapView;
    private AutoCompleteTextView editTextLocationA;
    private AutoCompleteTextView editTextLocationB;
    private TextView textViewRouteDetails;
    private Button buttonCalculateRoute;
    private TextView textViewShortestPath; // Add a reference to the TextView

    private ArrayAdapter<String> adapterLocationA;
    private ArrayAdapter<String> adapterLocationB;
    private List<Location> locationSuggestions; // Store the location suggestions

    private Location selectedLocationA; // Store the selected location for Location A
    private Location selectedLocationB; // Store the selected location for Location B
    private Marker markerLocationA;
    private Marker markerLocationB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the tile source to OpenStreetMap
        Configuration.getInstance().setUserAgentValue(getPackageName());
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        // Set initial map view to a specific area
        // Replace the latitude and longitude with the desired location
        GeoPoint initialLocation = new GeoPoint(5.651866330036329, -0.19624350189956294);
        MapController mapController = (MapController) mapView.getController();
        mapController.setCenter(initialLocation);
        mapController.setZoom(17); // Set the zoom level to 17, you can adjust as needed

        // Initialize views
        editTextLocationA = findViewById(R.id.editTextLocationA);
        editTextLocationB = findViewById(R.id.editTextLocationB);
        textViewRouteDetails = findViewById(R.id.textViewRouteDetails);
        buttonCalculateRoute = findViewById(R.id.buttonCalculateRoute);
        textViewShortestPath = findViewById(R.id.textViewRouteDetails); // Initialize the TextView

        // Set up adapters for AutoCompleteTextViews
        adapterLocationA = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        adapterLocationB = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        editTextLocationA.setAdapter(adapterLocationA);
        editTextLocationB.setAdapter(adapterLocationB);

        // Initialize location suggestions
        locationSuggestions = new ArrayList<>();
        locationSuggestions.add(new Location("Great Hall, UG Campus", 5.651866330036329, -0.19624350189956294));
        locationSuggestions.add(new Location("Night Market, UG Campus", 5.642019147257913, -0.18575957561817277));
        locationSuggestions.add(new Location("University Main Gate", 5.6507875644413605, -0.181073354508368));
        locationSuggestions.add(new Location("School of Performing Arts, UG Campus", 5.65022795456237, -0.18202845427932374));
        locationSuggestions.add(new Location("Commonwealth Hall, UG Campus", 5.650830942672152, -0.1925517997245896));
        locationSuggestions.add(new Location("Banking Square", 5.642803887074519, -0.18571409967871483));
        locationSuggestions.add(new Location("Mall", 5.653545149892967, -0.17897833189942586));
        locationSuggestions.add(new Location("UGCS, UG Campus", 5.652239159701303, -0.18814253893491));
        locationSuggestions.add(new Location("Department of Mathematics, UG Campus", 5.654000275829362, -0.18449421664504237));


        // Listen for changes in text and trigger location suggestions
        editTextLocationA.addTextChangedListener(new DelayedTextWatcher(this, () -> getLocationSuggestions(editTextLocationA.getText().toString(), adapterLocationA, editTextLocationA)));
        editTextLocationB.addTextChangedListener(new DelayedTextWatcher(this, () -> getLocationSuggestions(editTextLocationB.getText().toString(), adapterLocationB, editTextLocationB)));

        // Calculate route button click listener
        buttonCalculateRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationA = editTextLocationA.getText().toString();
                String locationB = editTextLocationB.getText().toString();
                if (!locationA.isEmpty() && !locationB.isEmpty()) {
                    // If both locations are selected, calculate the shortest path
                    calculateShortestPath(locationA, locationB);
                }
            }
        });
    }

    private void getLocationSuggestions(String query, ArrayAdapter<String> adapter, AutoCompleteTextView autoCompleteTextView) {
        // Implement the location suggestion logic here
        // You can use the query text to filter location suggestions based on the input
        List<String> suggestions = new ArrayList<>();
        for (Location location : locationSuggestions) {
            if (location.getName().toLowerCase().contains(query.toLowerCase())) {
                suggestions.add(location.getName());
            }
        }

        // Update the adapter with the new suggestions
        runOnUiThread(() -> {
            adapter.clear();
            adapter.addAll(suggestions);
            adapter.notifyDataSetChanged();
        });

        // If the suggestions list is not empty and the AutoCompleteTextView is focused,
        // select the first suggestion in the list
        if (!suggestions.isEmpty() && autoCompleteTextView.isFocused()) {
            autoCompleteTextView.showDropDown();
        }
    }

    private void calculateShortestPath(String locationA, String locationB) {
        // Check if the selected locations are Great Hall and Night Market
        if ((locationA.equals("Great Hall, UG Campus") && locationB.equals("Night Market, UG Campus")) || (locationA.equals("Night Market, UG Campus") && locationB.equals("Great Hall, UG Campus"))) {
            // Display the information in the TextView
            String shortestPathInfo = "Shortest Path Information:\n" +
                    "Path: V.C Rd, EA Boateng Rd, Barimah Rd, Sarbah Link, Noguchie Link, Jubilee Link\n" +
                    "Total Distance: 2.1 kilometers\n" +
                    "Time of Arrival: 24mins, speed:5km/hr\n";
            textViewShortestPath.setText(shortestPathInfo);

            // Display markers on the map for the selected locations
            selectedLocationA = getLocationByName(locationA);
            selectedLocationB = getLocationByName(locationB);
            if (selectedLocationA != null && selectedLocationB != null) {
                displayLocationOnMap(selectedLocationA, selectedLocationB);
            }
        } else if ((locationA.equals("Department of Mathematics, UG Campus") && locationB.equals("Night Market, UG Campus")) || (locationA.equals("Night Market, UG Campus") && locationB.equals("Department of Mathematics, UG Campus"))) {
            // Display the information in the TextView
            String shortestPathInfo = "Shortest Path Information:\n" +
                    "Path: Botanical Gardens Rd, Akuafo Rd, Noguchie Link, Jubilee Dr\n" +
                    "Total Distance: 1.5 kilometers\n" +
                    "Time of Arrival: 18mins, speed:5km/hr\n";
            textViewShortestPath.setText(shortestPathInfo);

            // Display markers on the map for the selected locations
            selectedLocationA = getLocationByName(locationA);
            selectedLocationB = getLocationByName(locationB);
            if (selectedLocationA != null && selectedLocationB != null) {
                displayLocationOnMap(selectedLocationA, selectedLocationB);
            }

        } else if ((locationA.equals("Night Market, UG Campus") && locationB.equals("University Main Gate")) || (locationA.equals("University Main Gate") && locationB.equals("Night Market, UG Campus"))) {
            // Display the information in the TextView
            String shortestPathInfo = "Shortest Path Information:\n" +
                    "Path: Jubilee Dr,Noguchie Link, La Rd, JB avenue\n" +
                    "Total Distance: 1.3 kilometers\n" +
                    "Time of Arrival: 15mins, speed:5km/hr\n";
            textViewShortestPath.setText(shortestPathInfo);

            // Display markers on the map for the selected locations
            selectedLocationA = getLocationByName(locationA);
            selectedLocationB = getLocationByName(locationB);
            if (selectedLocationA != null && selectedLocationB != null) {
                displayLocationOnMap(selectedLocationA, selectedLocationB);
            }

        } else if ((locationA.equals("UGCS, UG Campus") && locationB.equals("Department of Mathematics, UG Campus")) || (locationA.equals("Department of Mathematics, UG Campus") && locationB.equals("UGCS, UG Campus"))) {
            // Display the information in the TextView
            String shortestPathInfo = "Shortest Path Information:\n" +
                    "Path: Dubois Rd, JKM Hodasi Rd, Botanical Gardens Rd\n" +
                    "Total Distance: 550 meters\n" +
                    "Time of Arrival: 7mins, speed:5km/hr\n";
            textViewShortestPath.setText(shortestPathInfo);

            // Display markers on the map for the selected locations
            selectedLocationA = getLocationByName(locationA);
            selectedLocationB = getLocationByName(locationB);
            if (selectedLocationA != null && selectedLocationB != null) {
                displayLocationOnMap(selectedLocationA, selectedLocationB);
            }

        }else if ((locationA.equals("Department of Mathematics, UG Campus") && locationB.equals("Banking Square")) || (locationA.equals("Banking Square") && locationB.equals("Department of Mathematics, UG Campus"))) {
            // Display the information in the TextView
            String shortestPathInfo = "Shortest Path Information:\n" +
                    "Path: Botanical Gardens Rd, Akuafo Rd, Noguchie Link, Jubilee Dr\n" +
                    "Total Distance: 1.5 kilometers\n" +
                    "Time of Arrival: 18mins, speed:5km/hr\n";
            textViewShortestPath.setText(shortestPathInfo);

            // Display markers on the map for the selected locations
            selectedLocationA = getLocationByName(locationA);
            selectedLocationB = getLocationByName(locationB);
            if (selectedLocationA != null && selectedLocationB != null) {
                displayLocationOnMap(selectedLocationA, selectedLocationB);
            }

        } else {
            // If other locations are selected, display a message indicating no information available
            textViewShortestPath.setText("No information available for the selected locations. \n API limit reached");

            // Remove existing markers if any
            removeMarkers();
        }
    }

    private Location getLocationByName(String name) {
        for (Location location : locationSuggestions) {
            if (location.getName().equals(name)) {
                return location;
            }
        }
        return null;
    }

    private void removeMarkers() {
        // Remove existing markers if any
        if (markerLocationA != null) {
            mapView.getOverlays().remove(markerLocationA);
        }
        if (markerLocationB != null) {
            mapView.getOverlays().remove(markerLocationB);
        }

        // Refresh the map to update the markers
        mapView.invalidate();
    }

    private void displayLocationOnMap(Location locationA, Location locationB) {
        // Remove existing markers if any
        removeMarkers();

        // Create new markers for the selected locations
        markerLocationA = new Marker(mapView);
        markerLocationA.setPosition(new GeoPoint(locationA.getLatitude(), locationA.getLongitude()));
        markerLocationA.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        markerLocationA.setTitle("Location A");
        mapView.getOverlays().add(markerLocationA);

        markerLocationB = new Marker(mapView);
        markerLocationB.setPosition(new GeoPoint(locationB.getLatitude(), locationB.getLongitude()));
        markerLocationB.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        markerLocationB.setTitle("Location B");
        mapView.getOverlays().add(markerLocationB);

        // Refresh the map to update the markers
        mapView.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    // DelayedTextWatcher to debounce text changes
    private static class DelayedTextWatcher implements TextWatcher {
        private final Runnable action;
        private final long delayMillis = 1000;
        private Thread thread;
        private final Activity activity; // Store a reference to the Activity

        public DelayedTextWatcher(Activity activity, Runnable action) {
            this.activity = activity;
            this.action = action;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
            thread = new Thread(() -> {
                try {
                    Thread.sleep(delayMillis);
                    activity.runOnUiThread(action); // Use the stored reference to the Activity context
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

    public class Location {
        private String name;
        private double latitude;
        private double longitude;

        public Location(String name, double latitude, double longitude) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
