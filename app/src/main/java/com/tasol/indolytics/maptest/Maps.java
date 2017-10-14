package com.tasol.indolytics.maptest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.*;

/**
 * Created by tasol on 13/10/17.
 */

public class Maps extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    MapDBHelper mapDBHelper;
    java.util.List<MapsPojo> mapsList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mapDBHelper=new MapDBHelper(getActivity());
        mapsList = mapDBHelper.getAllRows();
        if(mapsList!=null&&mapsList.size()>0){
            for (int i = 0; i < mapsList.size(); i++) {
                MapsPojo mapsPojo=mapsList.get(i);
                Log.v("@@@WWE"," Name: "+mapsPojo.getName()+" Lat : "+mapsPojo.getLatitude()+" Long: "+mapsPojo.getLongitude());
            }
        }
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng pakwan = new LatLng(23.038,72.5123);
                LatLng vidhyapith = new LatLng(23.0432,72.5677);
                LatLng gujpress = new LatLng(22.9958, 72.5872);

                googleMap.addMarker(new MarkerOptions().position(pakwan).title("Pakwan").snippet("Marker Description"));
                googleMap.addMarker(new MarkerOptions().position(vidhyapith).title("Pakwan").snippet("Marker Description"));
                googleMap.addMarker(new MarkerOptions().position(gujpress).title("Pakwan").snippet("Marker Description"));
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(pakwan).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Maps");
    }

}
