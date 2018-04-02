package builderspace.restaurantfinder.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ParseException;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import builderspace.restaurantfinder.R;
import builderspace.restaurantfinder.model.Location;
import builderspace.restaurantfinder.model.Restaurant;
import builderspace.restaurantfinder.model.Result;
import builderspace.restaurantfinder.service.APIService;
import builderspace.restaurantfinder.utils.GooglePlayServiceChecker;
import builderspace.restaurantfinder.utils.NetworkChecker;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, APIService.fetchResults {

    private GoogleMap mMap;
    private APIService apiService;
    private IntentFilter connectivityIntentFilter;
    private BottomSheetDialog bottomSheetDialog;
    private View dialogView;
    private Boolean isOpenOnly;
    private Integer maxPrice;
    private Integer radius;
    private Place previousPlace;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetworkChecker.getInstance().isNetworkAvailable(context)) {
                Snackbar.make(findViewById(R.id.main_layout), getString(R.string.no_active_connection), Snackbar.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        apiService = new APIService(this);
        connectivityIntentFilter = new IntentFilter(CONNECTIVITY_ACTION);
        GooglePlayServiceChecker googlePlayServiceChecker = new GooglePlayServiceChecker();

        /*
         * Check for Google Play Services
         */

        if (!googlePlayServiceChecker.checkGooglePlayServices(this)) {
            Snackbar.make(findViewById(R.id.main_layout), getString(R.string.no_google_play_services), Snackbar.LENGTH_SHORT).show();
            finish();
        }


        /*
         *   Obtain the SupportMapFragment and get notified when the map is ready to be used.
         */

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /*
         * Set up auto complete fragment for search.
         */
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint(getString(R.string.default_city));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            /*
             *
             * Call the Places API with the area/city selected and restaurant as the type filter
             */
            @Override
            public void onPlaceSelected(Place place) {
                String s = (String) place.getName();

                //Update place data.
                previousPlace = place;

                apiService.getPlaceSearch(s, getString(R.string.place_type), radius, maxPrice, isOpenOnly);
            }

            @Override
            public void onError(Status status) {

            }
        });


        //Set up bottom sheet for filter.
        bottomSheetDialog = new BottomSheetDialog(MapsActivity.this);
        dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(dialogView);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_filter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();
            }
        });


        final Switch swOpenOnly = dialogView.findViewById(R.id.openCloseSwitch);
        final SeekBar skRadius = dialogView.findViewById(R.id.seekbar_radius);
        final SeekBar skPrice = dialogView.findViewById(R.id.seekbar_price);


        //Configure bottom sheet buttons.
        dialogView.findViewById(R.id.bt_submit_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                radius = skRadius.getProgress();
                maxPrice = skPrice.getProgress();
                isOpenOnly = swOpenOnly.isChecked();

                bottomSheetDialog.dismiss();

                //Get places using filter.
                if (previousPlace == null) {
                    apiService.getPlaceSearch(getString(R.string.default_city), getString(R.string.place_type), radius, maxPrice, isOpenOnly);
                } else {
                    apiService.getPlaceSearch(previousPlace.getName().toString(), getString(R.string.place_type), radius, maxPrice, isOpenOnly);
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
         *  Check for internet connection
         *  */
        if (!NetworkChecker.getInstance().isNetworkAvailable(this))
            Snackbar.make(findViewById(R.id.main_layout), getString(R.string.no_active_connection), Snackbar.LENGTH_SHORT).show();

            //Connected to internet. Search for San Francisco Restaurants by default.
        else
            apiService.getPlaceSearch(getString(R.string.default_city), getString(R.string.place_type), radius, maxPrice, isOpenOnly);


        /*
         OnClick Listener for Markers info windows on the map.
         Clicking will open a new activity with details of the marker.
         */
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(MapsActivity.this, DetailActivity.class);
                i.putExtra(getString(R.string.intent_key_id_tag), marker.getTag().toString());

                if (NetworkChecker.getInstance().isNetworkAvailable(MapsActivity.this))
                    startActivity(i);
            }
        });


    }

    /*
     * Receiver to check information of Network Changes
     */

    @Override
    public void parseResults(Result result) {

        //Empty map for new data points.
        mMap.clear();

        //Check conditions.
        if (result != null && result.getStatus() != null && result.getStatus().equals("OK")) {

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Restaurant restaurant : result.getRestaurants()) {
                Location pastLocation = restaurant.getGeometry().getLocation();

                LatLng latLng = new LatLng(pastLocation.getLatitude(), pastLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(restaurant.getName())
                        .snippet(restaurant.getFormattedAddress());

                Marker m = mMap.addMarker(markerOptions);
                m.setTag(restaurant.getResID());
                //m.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                builder.include(m.getPosition());
            }

            //try block to avoid forming bounds when no locations are selected.
            try {
                LatLngBounds latLngBounds = builder.build();
                int padding = 10;
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(latLngBounds, padding);
                mMap.animateCamera(cu);
            } catch (IllegalStateException | ParseException | NullPointerException e) {
                //Do not animate/move camera.
            }
        } else {
            Snackbar.make(findViewById(R.id.main_layout), getString(R.string.error_message), Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, connectivityIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
