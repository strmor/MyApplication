package com.example.morten.myapplication2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity2 extends ActionBarActivity {


    Timer myTimer;

    TextView viewById;
    TextView logtextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);


//        myTimer = new Timer();
//        myTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                TimerMethod();
//            }
//
//        }, 0, 5000);


        viewById = (TextView) this.findViewById(R.id.errorText);
        logtextView = (TextView) this.findViewById(R.id.logText);


//        Class<?> emClass = null;
//        try {
//            emClass = Class.forName("android.net.ethernet.EthernetManager");
//        } catch (ClassNotFoundException ex) {
//            String stackTrace = Log.getStackTraceString(ex);
//
//            viewById.setText(stackTrace);
//        }
//
//        Object emInstance = getSystemService("ethernet");
//
//        Method methodSetEthEnabled = null;
//        try {
//            methodSetEthEnabled = emClass.getMethod("setEthEnabled", Boolean.TYPE);
//        } catch (NoSuchMethodException ex) {
//            // TODO Auto-generated catch block
//            String stackTrace = Log.getStackTraceString(ex);
//
//            viewById.setText(stackTrace);
//        }
//        methodSetEthEnabled.setAccessible(true);
//
//        try {
//            // new Boolean(true) to enable, new Boolean(false) to disable
//            methodSetEthEnabled.invoke(emInstance, new Boolean(false));
//        } catch (Exception ex) {
//            String stackTrace = Log.getStackTraceString(ex);
//
//            viewById.setText(stackTrace);
//        }
//
//        logtextView.append(emInstance + "");


        Runtime proc = Runtime.getRuntime();

        try {
            proc.exec(new String[]{"su", "-c", "netcfg eth0 up"});
        } catch (IOException e) {
            String stackTrace = Log.getStackTraceString(e);

            viewById.setText(stackTrace);
        }

    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {


            ReloadWithTryCatch();

        }
    };

    private void ReloadWithTryCatch() {
        try
        {
            Reload();
        }
        catch(Exception ex)
        {
            String stackTrace = Log.getStackTraceString(ex);

            viewById.setText(stackTrace);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.reload)
        {
            ReloadWithTryCatch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Reload() throws SocketException {


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        logtextView.append(currentDateandTime + " Start reload\n");
        logtextView.setMovementMethod(new ScrollingMovementMethod());

        ArrayList<Networkdevice> networkDevices = new ArrayList<>();


            for (NetworkInterface n : Collections.list(NetworkInterface.getNetworkInterfaces()))
            {
                networkDevices.add(new Networkdevice(n));
            }



        // Get ListView object from xml
        ListView listView = (ListView) findViewById(R.id.listView);

        // Defined Array values to show in ListView
//        ArrayList<String> values = new ArrayList<String>();


//        for (Networkdevice n : networkDevices)
//        {
//            values.add(n.toString());
//        }


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<Networkdevice> adapter = new ArrayAdapter<Networkdevice>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, networkDevices);




        // Assign adapter to ListView
        listView.setAdapter(adapter);



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> a,
//                                    View v, int position, long id) {
//                NetworkInterface n = (NetworkInterface) a.getItemAtPosition(position);
//
//                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
//                intent.putExtra("com.example.cities.City", city);
//                startActivity(intent);
//            }
//        });



        // Item Click Listener for the listview
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {

                Networkdevice n = (Networkdevice) parent.getItemAtPosition(position);

//
//                // Getting the Container Layout of the ListView
//                LinearLayout linearLayoutParent = (LinearLayout) container;
//
//                // Getting the inner Linear Layout
//                LinearLayout linearLayoutChild = (LinearLayout ) linearLayoutParent.getChildAt(1);
//
//                // Getting the Country TextView
//                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);

                Toast.makeText(getBaseContext(), n.toDetailedString(), Toast.LENGTH_LONG).show();
            }
        };


        listView.setOnItemClickListener(itemClickListener);

//        // ListView Item Click Listener
//        listView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                // ListView Clicked item index
//                int itemPosition     = position;
//
//                // ListView Clicked item value
//                String  itemValue    = (String) listView.getItemAtPosition(position);
//
//                // Show Alert
//                Toast.makeText(getApplicationContext(),
//                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
//                        .show();
//
//            }
//
//        });


        String currentDateandTime2 = sdf.format(new Date());

        logtextView.append(currentDateandTime2+ " Reload OK\n");

        logtextView.setMovementMethod(new ScrollingMovementMethod());

    }
}


