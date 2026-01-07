package com.example.complecourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConnectivityActivity extends AppCompatActivity implements View.OnClickListener {
    Button wifiBtn, btBtn, sensorBtn, smsBtn, emailBtn, notifBtn, cameraBtn;
    WifiManager wifiManager;

    private static final int SMS_PERMISSION_CODE = 100;

    private static final String CHANNEL_ID = "channel1";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);

        wifiBtn = findViewById(R.id.btnWifi);
        btBtn = findViewById(R.id.btnBluetooth);

        smsBtn = findViewById(R.id.btnSms);
        emailBtn = findViewById(R.id.btnEmail);
        notifBtn = findViewById(R.id.btnNotification);
        cameraBtn = findViewById(R.id.btnCamera);

        wifiBtn.setOnClickListener(this);
        btBtn.setOnClickListener(this);
        smsBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);
        notifBtn.setOnClickListener(this);
        cameraBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnWifi) {
            callWifiMthd();
        } else if (v.getId() == R.id.btnBluetooth) {
            callBtn();
        } else if (v.getId() == R.id.btnSms) {
           //callSMS();
          Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("vnd.android-dir/mms-sms");
            startActivity(intent);
        } else if (v.getId() == R.id.btnEmail) {
            callEmail();
        } else if (v.getId() == R.id.btnNotification) {
            createNotificationChannel();


            // Check & request permission if needed
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                            REQUEST_CODE_POST_NOTIFICATIONS
                    );
                    return;
                }
            }

            // If permission already granted -> show notification
            showNotification();
        } else if (v.getId() == R.id.btnCamera) {
            callCamera();
        }
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Demo Notification")
                .setContentText("This is a test notification")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return; // Permission not granted yet
        }

        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Demo Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("This is a demo notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void callSensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    private void callCamera() {
        Intent intent = new Intent(ConnectivityActivity.this,CameraActivity.class);
        startActivity(intent);

    }



    private void callEmail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"test@example.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Demo Email");
        email.putExtra(Intent.EXTRA_TEXT, "Hello from Android App!");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose Email App"));
    }

    private void callSMS() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        } else {
            sendMySMS();
        }

    }

    private void sendMySMS() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("9876543210", null, "Hello from app!", null, null);
            Toast.makeText(this, "SMS Sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "SMS failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendMySMS();
            } else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted -> show notification
                showNotification();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 120) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted -> show notification
              getWifiName(wifiManager);
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callBtn() {
        Intent intent = new Intent(ConnectivityActivity.this,BluetoothActivity.class);
        startActivity(intent);
    }


    private void callWifiMthd() {
         wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 120);
        getWifiName(wifiManager);


    }

    private void getWifiName(WifiManager wifiManager) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isLocationEnabled){
            Toast.makeText(this, "Please enable Location to get Wi-Fi SSID", Toast.LENGTH_SHORT).show();
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getNetworkId() != -1) {
            String ssid = wifiInfo.getSSID();
            Log.d("ssid",""+ssid);
            if(ssid.startsWith("\"") && ssid.endsWith("\"")){
                ssid = ssid.substring(1, ssid.length()-1); // remove quotes
            }
            Toast.makeText(this, "Connected to: " + ssid, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not connected to any Wi-Fi", Toast.LENGTH_SHORT).show();
        }
    }
}