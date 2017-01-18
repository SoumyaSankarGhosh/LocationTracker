package com.example.kiit.mybagdkart;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kiit.mybagdkart.adapter.LocationViewAdapter;
import com.example.kiit.mybagdkart.bean.Location;
import com.example.kiit.mybagdkart.utilities.GPSTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView list;
    LocationViewAdapter adapter;
    SearchView editsearch;
    ArrayList<Location> arraylist = new ArrayList<Location>();
    ImageView btnShowLocation;
    TextView myAddress;
    GPSTracker gps,gps1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addresspage_activity_main);

        loadData();

        initial();
        automatic();
        onClick();


        adapter=new LocationViewAdapter(this,arraylist);
        list.setAdapter(adapter);


        editsearch.setOnQueryTextListener(this);
    }

    public void loadData(){
        arraylist.add(new Location("Rajarhat New Town"));
        arraylist.add(new Location("Chakala"));
        arraylist.add(new Location("E.m.Bypass,Kolkata,India"));
        arraylist.add(new Location("Mysore Road"));
        arraylist.add(new Location("Beliaghate"));
        arraylist.add(new Location("Brae Bazar"));
    }
    public void initial(){
        list=(ListView)findViewById(R.id.listView_location);
        editsearch=(SearchView) findViewById(R.id.searchView_inputSearchLocation);

        myAddress = (TextView)findViewById(R.id.textView_myaddress);
        btnShowLocation = (ImageView) findViewById(R.id.imageView_btnShowLocation);

    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    public void onClick(){
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                myAddress.setVisibility(View.VISIBLE);
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    //  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    Geocoder geocoder=new Geocoder(MainActivity.this, Locale.ENGLISH);
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude,longitude, 1);

                        if(addresses != null) {
                            Address returnedAddress = addresses.get(0);
                          //  StringBuilder strReturnedAddress = new StringBuilder("Address: ");
                            StringBuilder strReturnedAddress = new StringBuilder("");
                           /* for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {

                                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");


                            }*/
                            strReturnedAddress.append(returnedAddress.getAddressLine(1));
                            myAddress.setText(strReturnedAddress.toString());

                        }
                        else{
                            myAddress.setText("No Address returned!");
                        }

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        myAddress.setText("Canont get Address!");
                    }

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();

                }
            }
        });
    }
    public void automatic(){
        gps1 = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps1.canGetLocation()){
            double latitude1 = gps1.getLatitude();
            double longitude1 = gps1.getLongitude();
            //  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            Geocoder geocoder=new Geocoder(MainActivity.this, Locale.ENGLISH);
            try {
                List<Address> addresses1 = geocoder.getFromLocation(latitude1,longitude1, 1);

                if(addresses1 != null ) {
                    Address returnedAddress = addresses1.get(0);
                    //  StringBuilder strReturnedAddress = new StringBuilder("Address: ");
                    StringBuilder strReturnedAddress = new StringBuilder("");
                            for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {

                                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");


                            }
                   // strReturnedAddress.append(returnedAddress.getAddressLine(1));
                    myAddress.setText(strReturnedAddress.toString());

                }
                else{
                    myAddress.setText("No Address returned!");
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                myAddress.setText("Canont get Address!");
            }

        }else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps1.showSettingsAlert();
        }
    }
}
