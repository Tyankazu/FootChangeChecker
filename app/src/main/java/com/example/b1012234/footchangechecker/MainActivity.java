package com.example.b1012234.footchangechecker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.*;


public class MainActivity extends ActionBarActivity implements LocationListener{

    LocationManager mLocationManager = null;
    private static final String LOG_TAG ="Location";

    private SimpleDateFormat sdf;
    BufferedWriter bw;
    Date date;
    private String path;
    private String lati;
    private String longi;
    private String sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationManager =(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        // クリックイベントを取得したいボタン
        Button Start_bt = (Button) findViewById(R.id.btnStart);
        Button Stop_bt = (Button) findViewById(R.id.btnStop);
        Button ABch_bt = (Button) findViewById(R.id.btnAB);
        Button BAch_bt = (Button) findViewById(R.id.btnBA);

        date = new Date();//現在時刻の取得

        // クリックリスナーを登録
        Start_bt.setOnClickListener(new View.OnClickListener() {

            //クリック時に呼ばれるメソッド
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "START", Toast.LENGTH_SHORT).show();
                sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//時刻の出力フォーマット作成
                System.out.println(sdf.format(date));

            }
        });


        // クリックリスナーを登録
        Stop_bt.setOnClickListener(new View.OnClickListener() {

            //クリック時に呼ばれるメソッド
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "STOP", Toast.LENGTH_SHORT).show();
                System.out.println("Wrote File");
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        ABch_bt.setOnClickListener(new View.OnClickListener() {

            //クリック時に呼ばれるメソッド
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "move to brake", Toast.LENGTH_SHORT).show();

                String fileName = sdf.format(date) + "FCC" + ".csv";
                path = Environment.getExternalStorageDirectory() + "/" + fileName;
                File file = new File(path);

                file.getParentFile().mkdir();


                Calendar time = Calendar.getInstance();
                int year = time.get(time.YEAR);
                int month = time.get(time.MONTH);
                int day = time.get(time.DAY_OF_MONTH);
                int hour = time.get(time.HOUR_OF_DAY);
                int minute = time.get(time.MINUTE);
                int second = time.get(time.SECOND);
                int ms = time.get(time.MILLISECOND);

                String nowtime = valueOf(year) + "/" +
                        valueOf(month + 1) + "/" + valueOf(day) + "_" + valueOf(hour) + ":"
                        + valueOf(minute) + ":" + valueOf(second) + ":" + valueOf(ms);


                String ChangeAB = nowtime + "," +"AB"+","+
                        lati + "," +
                        longi + "," +
                        sp + "\n";
                System.out.println("pushBA"+lati);
                System.out.println("pushBA"+longi);
                System.out.println("pushBA"+sp);

                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file, true);
                    OutputStreamWriter writer = new OutputStreamWriter(fos);
                    bw = new BufferedWriter(writer);
                    bw.write(ChangeAB);
                    bw.flush();


                    System.out.println("save");
                } catch (UnsupportedEncodingException k) {
                    k.printStackTrace();
                } catch (FileNotFoundException k) {
                    String message = k.getMessage();
                    k.printStackTrace();
                } catch (IOException k) {
                    String message = k.getMessage();
                    k.printStackTrace();
                }

            }
        });
        BAch_bt.setOnClickListener(new View.OnClickListener() {

            //クリック時に呼ばれるメソッド
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "move to Accel", Toast.LENGTH_SHORT).show();


                String fileName = sdf.format(date) + "FCC" + ".csv";
                path = Environment.getExternalStorageDirectory() + "/" + fileName;
               File file = new File(path);
//
//                file.getParentFile().mkdir();


                Calendar time = Calendar.getInstance();
                int year = time.get(time.YEAR);
                int month = time.get(time.MONTH);
                int day = time.get(time.DAY_OF_MONTH);
                int hour = time.get(time.HOUR_OF_DAY);
                int minute = time.get(time.MINUTE);
                int second = time.get(time.SECOND);
                int ms = time.get(time.MILLISECOND);

                String nowtime = valueOf(year) + "/" +
                        valueOf(month + 1) + "/" + valueOf(day) + "_" + valueOf(hour) + ":"
                        + valueOf(minute) + ":" + valueOf(second) + ":" + valueOf(ms);

                String ChangeBA = nowtime + "," +"BA"+","+
                        lati + "," +
                        longi + "," +
                        sp + "\n";
                System.out.println("pushBA"+lati);
                System.out.println("pushBA"+longi);
                System.out.println("pushBA"+sp);

                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file, true);
                    OutputStreamWriter writer = new OutputStreamWriter(fos);
                    bw = new BufferedWriter(writer);
                    bw.write(ChangeBA);
                    bw.flush();


                    System.out.println("save2");
                } catch (UnsupportedEncodingException k) {
                    k.printStackTrace();
                } catch (FileNotFoundException k) {
                    String message = k.getMessage();
                    k.printStackTrace();
                } catch (IOException k) {
                    String message = k.getMessage();
                    k.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Location lastLocation = mLocationManager.getLastKnownLocation
                (LocationManager.GPS_PROVIDER);
        updateDisplayedInfo(lastLocation);
        //位置情報更新要求(リスナの登録)
        mLocationManager.requestLocationUpdates
                (LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //リスナの解除
        mLocationManager.removeUpdates(this);
    }

    public void updateDisplayedInfo(Location location){
        if (location == null){
            Log.e(LOG_TAG, "location is null.");
            return;
        }
        //緯度の表示更新
        TextView lat_value = (TextView)findViewById(R.id.latitude);
        lat_value.setText(Double.toString(location.getLatitude()));
        //経度の表示更新
        TextView lon_value = (TextView)findViewById(R.id.longitude);
        lon_value.setText(Double.toString(location.getLongitude()));
        //スピードの表示更新
        TextView spd_value = (TextView)findViewById(R.id.speed);
        spd_value.setText(Float.toString(location.getSpeed()));
        lati = Double.toString(location.getLatitude());
        longi = Double.toString(location.getLongitude());
        sp = Float.toString(location.getSpeed());
    }

    public void onLocationChanged(Location location){
        updateDisplayedInfo(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
