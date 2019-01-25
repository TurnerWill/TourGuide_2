package com.turnerwill.android.tourguide_2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.location.Locations;
import com.tomtom.online.sdk.map.BaseMarkerBalloon;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MarkerAnchor;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteResult;
import com.tomtom.online.sdk.routing.data.RouteType;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResponse;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.tomtom.online.sdk.map.MapConstants.DEFAULT_ZOOM_LEVEL;
import static java.security.AccessController.getContext;

public class DetailActivityMap extends AppCompatActivity implements OnMapReadyCallback, TomtomMapCallback.OnMapLongClickListener {

    ImageView mImage;
    TextView mDescription;
    TextView mName;
    private String phone;
    private String latitude;
    private String longitude;
    private String description;
    private String targetName;
    private TomtomMap tomtomMap;
    private SearchApi searchApi;
    private RoutingApi routingApi;
    private Route route;
    private LatLng departurePosition;
    private LatLng destinationPosition;
    private LatLng wayPointPosition;
    private Icon departureIcon;
    private Icon destinationIcon;
    private ImageButton btnSearch;
    private EditText editTextPois;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            targetName = mBundle.getString("Title");
            latitude = mBundle.getString("Latitude");
            longitude = mBundle.getString("Longitude");
            description = mBundle.getString("Description");
        }
        initTomTomServices();
        initUIViews();
        setupUIViewListeners();

    }

    @Override
    public void onMapReady(@NonNull TomtomMap tomtomMap) {
        LatLng syr = new LatLng(43.04, -76.14); // aux
        LatLng target = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
        this.tomtomMap = tomtomMap;
        this.tomtomMap.setMyLocationEnabled(true);
        this.tomtomMap.centerOn(target.getLatitude(),target.getLongitude(),12, MapConstants.ORIENTATION_NORTH); // aux

        clearMap();

        MarkerBuilder markerBuilder = new MarkerBuilder(syr) //start here
                .icon(Icon.Factory.fromResources(this, R.drawable.ic_favourites))
                .markerBalloon(new SimpleMarkerBalloon("Welcome to Syracuse"))
                .tag("more information in tag").iconAnchor(MarkerAnchor.Bottom)
                .decal(true); //By default is false
        this.tomtomMap.addMarker(markerBuilder);


        MarkerBuilder targetMarker = new MarkerBuilder(target)
                .icon(Icon.Factory.fromResources(this, R.drawable.ic_markedlocation))
                .markerBalloon(new SimpleMarkerBalloon(targetName))
                .tag("more information in tag").iconAnchor(MarkerAnchor.Bottom)
                .decal(true);
        this.tomtomMap.addMarker(targetMarker);


        Toast.makeText(DetailActivityMap.this, "Syracuse, NY", Toast.LENGTH_SHORT).show();// end here

        this.tomtomMap.addOnMapLongClickListener(this);
        this.tomtomMap.getMarkerSettings().setMarkersClustering(true);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        if (isDeparturePositionSet() && isDestinationPositionSet()) {
            clearMap();
        } else {
            handleLongClick(latLng);
        }

    }

    private void handleLongClick(@NonNull LatLng latLng) {

        searchApi.reverseGeocoding(new ReverseGeocoderSearchQueryBuilder(latLng.getLatitude(), latLng.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ReverseGeocoderSearchResponse>() {
                    @Override
                    public void onSuccess(ReverseGeocoderSearchResponse response) {
                        processResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleApiError(e);
                    }

                    private void processResponse(ReverseGeocoderSearchResponse response) {
                        if (response.hasResults()) {
                            processFirstResult(response.getAddresses().get(0).getPosition());
                        } else {
                            Toast.makeText(DetailActivityMap.this, getString(R.string.geocode_no_results), Toast.LENGTH_SHORT).show();
                        }
                    }

                    private void processFirstResult(LatLng geocodedPosition) {
                        if (!isDeparturePositionSet()) {
                            setAndDisplayDeparturePosition(geocodedPosition);
                        } else {
                            destinationPosition = geocodedPosition;
                            tomtomMap.removeMarkers();
                            drawRoute(departurePosition, destinationPosition);
                        }
                    }

                    private void setAndDisplayDeparturePosition(LatLng geocodedPosition) {
                        departurePosition = geocodedPosition;
                        createMarkerIfNotPresent(departurePosition, departureIcon);
                    }
                });
    }

    private boolean isDestinationPositionSet() {
        return destinationPosition != null;
    }

    private boolean isDeparturePositionSet() {
        return departurePosition != null;
    }

    private void handleApiError(Throwable e) {
        Toast.makeText(DetailActivityMap.this, getString(R.string.api_response_error, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
    }

    private RouteQuery createRouteQuery(LatLng start, LatLng stop, LatLng[] wayPoints) {
        return (wayPoints != null) ?
                new RouteQueryBuilder(start, stop).withWayPoints(wayPoints).withRouteType(RouteType.FASTEST):
                new RouteQueryBuilder(start, stop).withRouteType(RouteType.FASTEST);
    }

    private void drawRoute(LatLng start, LatLng stop) {
        wayPointPosition = null;
        drawRouteWithWayPoints(start, stop, null);
    }

    private void drawRouteWithWayPoints(LatLng start, LatLng stop, LatLng[] wayPoints) {
        RouteQuery routeQuery = createRouteQuery(start, stop, wayPoints);
        routingApi.planRoute(routeQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<RouteResult>(){

                    @Override
                    public void onSuccess(RouteResult routeResult) {
                        displayRoutes(routeResult.getRoutes());
                        tomtomMap.displayRoutesOverview();
                    }

                    private void displayRoutes(List<FullRoute> routes) {
                        for (FullRoute fullRoute : routes) {
                            route = tomtomMap.addRoute(new RouteBuilder(fullRoute.getCoordinates())
                                    .startIcon(departureIcon).endIcon(destinationIcon).isActive(true));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleApiError(e);
                        clearMap();
                    }
                });
    }

    private void createMarkerIfNotPresent(LatLng position, Icon icon) {
        Optional optionalMarker = tomtomMap.findMarkerByPosition(position);
        if (!optionalMarker.isPresent()) {
            tomtomMap.addMarker(new MarkerBuilder(position)
                    .icon(icon));
        }
    }


    private void initTomTomServices() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getAsyncMap(this);
        searchApi = OnlineSearchApi.create(this);
        routingApi = OnlineRoutingApi.create(this);
    }

    private void initUIViews() {
        departureIcon = Icon.Factory.fromResources(DetailActivityMap.this, R.drawable.ic_map_route_departure);
        destinationIcon = Icon.Factory.fromResources(DetailActivityMap.this, R.drawable.ic_map_route_destination);
        btnSearch = findViewById(R.id.btn_main_poisearch);
        editTextPois = findViewById(R.id.edittext_main_poisearch);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setupUIViewListeners() {

        View.OnClickListener searchButtonListener = getSearchButtonListener();
        btnSearch.setOnClickListener(searchButtonListener);

    }

    @NonNull
    private View.OnClickListener getSearchButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchClick(v);
            }

            private void handleSearchClick(View v) {
                if (isRouteSet()) {
                    Optional<CharSequence> description = Optional.fromNullable(v.getContentDescription());

                    if (description.isPresent()) {
                        editTextPois.setText(description.get());
                        v.setSelected(true);
                    }
                    if (isWayPointPositionSet()) {
                        tomtomMap.clear();
                        drawRoute(departurePosition, destinationPosition);
                    }
                    String textToSearch = editTextPois.getText().toString();
                    if (!textToSearch.isEmpty()) {
                        tomtomMap.removeMarkers();
                        searchAlongTheRoute(route, textToSearch);
                    }
                }
            }

            private boolean isRouteSet() {
                return route != null;
            }

            private boolean isWayPointPositionSet() {
                return wayPointPosition != null;
            }

            private void searchAlongTheRoute(Route route, final String textToSearch) {
                final Integer MAX_DETOUR_TIME = 1000;
                final Integer QUERY_LIMIT = 10;

                searchApi.alongRouteSearch(new AlongRouteSearchQueryBuilder(textToSearch, route.getCoordinates(),
                        MAX_DETOUR_TIME).withLimit(QUERY_LIMIT))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<AlongRouteSearchResponse>() {
                            @Override
                            public void onSuccess(AlongRouteSearchResponse response) {
                                displaySearchResults(response.getResults());
                            }

                            private void displaySearchResults(List<AlongRouteSearchResult> results) {
                                if (!results.isEmpty()) {
                                    for (AlongRouteSearchResult result : results) {
                                        createAndDisplayCustomMarker(result.getPosition(), result);
                                    }
                                    tomtomMap.zoomToAllMarkers();
                                } else {
                                    Toast.makeText(DetailActivityMap.this, String.format(getString(R.string.no_search_results), textToSearch),
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            private void createAndDisplayCustomMarker(LatLng postion, AlongRouteSearchResult result) {
                                String address = result.getAddress().getFreeformAddress();
                                String poiName = result.getPoi().getName();

                                BaseMarkerBalloon markerBalloonData = new BaseMarkerBalloon();
                                markerBalloonData.addProperty(getString(R.string.poi_name_key), poiName);
                                markerBalloonData.addProperty(getString(R.string.address_key), address);

                                MarkerBuilder markerBuilder = new MarkerBuilder(postion)
                                        .markerBalloon(markerBalloonData)
                                        .shouldCluster(true);
                                tomtomMap.addMarker(markerBuilder);
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleApiError(e);
                            }
                        });
            }
        };
    }

    private void clearMap() {
        tomtomMap.clear();
        departurePosition = null;
        destinationPosition = null;
        route = null;
    }
}
