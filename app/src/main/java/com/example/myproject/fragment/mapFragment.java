package com.example.myproject.fragment;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.AppCompatButton;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//
//import com.example.myproject.Activity.MainActivity;
//import com.example.myproject.HttpURL;
//import com.example.myproject.R;
//import com.example.myproject.model.Users;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.Circle;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.maps.android.SphericalUtil;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class mapFragment extends Fragment implements OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener {
//
//    GoogleMap googleMap;
//    GoogleApiClient googleApiClient;
//    String userAddress;
//    String userPostCode;
//    LocationRequest locationRequest;
//    EditText latitude;
//    EditText lontitude;
//    AppCompatButton appCompatButtonMove;
//    Users _loginUser;
//    View vMain;
//    String url;
//    String data;
//    InputStream inputStream;
//    BufferedReader bufferedReader;
//    StringBuilder stringBuilder;
//
//    public mapFragment() {
//
//    }
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View vMain = inflater.inflate(R.layout.fragment_map, container, false);
//        _loginUser = ((MainActivity) getActivity()).getLoginUser();
//        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
//                .findFragmentById(R.id.maps);
//        userAddress = _loginUser.getAddress();
//        userPostCode = _loginUser.getPostcode();
//        mapFragment.getMapAsync(this);
//        return vMain;
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap = googleMap;
//        googleMap.getUiSettings().setZoomControlsEnabled(true);
//        //LatLng HOME = new LatLng(0,0);
//
//        googleApiClient = new GoogleApiClient.Builder(this.getContext())
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
//        googleApiClient.connect();
//    }
//
//    public void GetLantitude(String address, int postcode) {
//        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?");
//        stringBuilder.append("address=" + address + " + " + postcode);
//        stringBuilder.append("&key=" + "AIzaSyCi6R1qiq0Eb7YlVvAkHeNLBWWJd54-ZAQ");
//        String url = stringBuilder.toString();
//        Object dataTransfer[] = new Object[2];
//        dataTransfer[0] = googleMap;
//        dataTransfer[1] = url;
//        new GetLongtitude().execute(dataTransfer);
//    }
//
//    public class GetLongtitude extends AsyncTask<Object, String, List<String>> {
//        @Override
//        protected List<String> doInBackground(Object... objects) {
//            googleMap = (GoogleMap) objects[0];
//            url = (String) objects[1];
//
//            List<String> mix = new ArrayList<>();
//            mix.add(new HttpURL().getHttpUrlConnection(url));
//            mix.add(userAddress);
//            mix.add(String.valueOf(userPostCode));
//            return mix;
//        }
//
//        @Override
//        protected void onPostExecute(List<String> mix) {
//
//            try {
//                JSONObject parentObject = null;
//                try {
//                    parentObject = new JSONObject(mix.get(0));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                JSONArray resultArray = parentObject.getJSONArray("results");
//                String latitude = "";
//                String longitude = "";
//
//                JSONObject jsonObject = resultArray.getJSONObject(0);
//                JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");
//                latitude = locationObj.getString("lat");
//                longitude = locationObj.getString("lng");
//                park(Double.valueOf(latitude), Double.valueOf(longitude));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        public void park(Double latitude, Double longitude) {
//            LatLng HOME = new LatLng(latitude, longitude);
//            MarkerOptions mHome = new MarkerOptions()
//                    .position(HOME)
//                    .title("Home");
//            googleMap.addMarker(mHome);
//            Circle circle = googleMap.addCircle(new CircleOptions()
//                    .center(HOME)
//                    .radius(5000)
//                    .strokeColor(Color.RED));
//            googleMap.moveCamera(getZoomForDistance(HOME, 5000));
//            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//            sb.append("&location=" + latitude + "," + longitude);
//            sb.append("&radius=" + 5000);
//            sb.append("&types=" + "park");
//            sb.append("&key=" + "AIzaSyCi6R1qiq0Eb7YlVvAkHeNLBWWJd54-ZAQ");
//            String url = sb.toString();
//            Object dataTransfer[] = new Object[2];
//            dataTransfer[0] = googleMap;
//            dataTransfer[1] = url;
//            new NearByParks().execute(dataTransfer);
//        }
//
//        public class NearByParks extends AsyncTask<Object, String, String> {
//            @Override
//            protected String doInBackground(Object... objects) {
//                googleMap = (GoogleMap) objects[0];
//                url = (String) objects[1];
//                return new HttpURL().getHttpUrlConnection(url);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                try {
//                    JSONObject parentObject = null;
//                    try {
//                        parentObject = new JSONObject(s);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    JSONArray resultArray = parentObject.getJSONArray("results");
//
//                    for (int i = 0; i < resultArray.length(); i++) {
//                        JSONObject jsonObject = resultArray.getJSONObject(i);
//                        JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");
//                        String latitude = locationObj.getString("lat");
//                        String longitude = locationObj.getString("lng");
//                        String parkName = jsonObject.getString("name");
//                        String vicinity = jsonObject.getString("vicinity");
//                        String icon = jsonObject.getString("icon");
//                        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(latLng)
//                                .title(parkName);
//                        googleMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//            }
//        }
//    }
//    private CameraUpdate getZoomForDistance(LatLng originalPosition, double distance){
//        LatLng rightBottom = SphericalUtil.computeOffset(originalPosition,distance,135);
//        LatLng leftTop = SphericalUtil.computeOffset(originalPosition,distance,-45);
//        LatLngBounds sBounds = new LatLngBounds(new LatLng(rightBottom.latitude,leftTop.longitude),new LatLng(leftTop.latitude,rightBottom.longitude));
//        return CameraUpdateFactory.newLatLngBounds(sBounds,0);
//
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        locationRequest = new LocationRequest().create();
//        locationRequest.setInterval(1000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//}

import android.support.v4.app.Fragment;

public class mapFragment extends Fragment {

}
