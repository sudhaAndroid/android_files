package com.example.complecourseproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;

/* About this project
1.contains widgetproject- In MainActivity
2. Intent Handling - IntentHandlingActivity
3. Audio/vide Activity- AudioVideoActivity
4.Bottom Navigation - FragmentActivity
5. TabActivity - Inside FragmentActivity
6. Grid Activity - When Click MainActivity,s textView -> click GridActivty
7. RecyclerActivity- When Click MainActivity,s Button -> click RecyclerActivity
8.Connectivity - ConnectivityActivity
9. MaterialDesign - MaterialDesignActivity -
10.FirebaseIntegration MainActivity Having some code along with MyFirebaseMessagingService
11.Retrofit Activity - under retrofit folder with APIIntegrationActivity
12 .Room Database - which is seperate project ->  in the name of RoomDBRecycler */

/*The first page of the screen ->SplashScreen*/
public class MainActivity extends AppCompatActivity {
    NetworkChangeReceiver networkReceiver = new NetworkChangeReceiver();
    private AdView adView;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    String response = "{\n" +
            "  \"status\": \"success\",\n" +
            "  \"user\": {\n" +
            "    \"id\": 101,\n" +
            "    \"name\": \"Sudha Pandian\",\n" +
            "    \"email\": \"sudha@example.com\",\n" +
            "    \"orders\": [\n" +
            "      {\n" +
            "        \"order_id\": 1001,\n" +
            "        \"date\": \"2025-10-22\",\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"product\": \"Mobile Phone\",\n" +
            "            \"price\": 12000,\n" +
            "            \"quantity\": 1\n" +
            "          },\n" +
            "          {\n" +
            "            \"product\": \"Phone Case\",\n" +
            "            \"price\": 500,\n" +
            "            \"quantity\": 2\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"order_id\": 1002,\n" +
            "        \"date\": \"2025-10-20\",\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"product\": \"Laptop\",\n" +
            "            \"price\": 55000,\n" +
            "            \"quantity\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}\n";

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure sign-in to request the user's ID, email address, and basic profile.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.signInButton).setOnClickListener(v -> signIn());
        findViewById(R.id.signOutButton).setOnClickListener(v -> signOut());

        MobileAds.initialize(this, initializationStatus -> {});

        // To get Device Id
        new Thread(() -> {
            try {
                AdvertisingIdClient.Info idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                String deviceId = idInfo.getId();
                Log.d("AdMobDeviceID", "Advertising ID: " + deviceId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        // Add your device as test device (get ID from Logcat)
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder()
                        .setTestDeviceIds(Arrays.asList("B3765D134E2AB062B630581023C46F40"))
                        .build()
        );

        // response
        try {
            JSONObject root = new JSONObject(response);
            String status = root.getString("status");


            JSONObject userObj = root.getJSONObject("user");
            String userName = userObj.getString("name");
            String userEmail = userObj.getString("email");

            JSONArray ordersArray = userObj.getJSONArray("orders");
            for (int i = 0; i < ordersArray.length(); i++) {
                JSONObject order = ordersArray.getJSONObject(i);
                int orderId = order.getInt("order_id");
                String orderDate = order.getString("date");

                JSONArray itemsArray = order.getJSONArray("items");
                for (int j = 0; j < itemsArray.length(); j++) {
                    JSONObject item = itemsArray.getJSONObject(j);
                    String product = item.getString("product");
                    int price = item.getInt("price");
                    int quantity = item.getInt("quantity");

                    Log.d("Item", "Product: " + product + ", Price: " + price + ", Qty: " + quantity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Load Ad
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //To find keyhash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.complecourseproject",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        // facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.setClientToken(getString(R.string.facebook_client_token));
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);

        // Ask permissions
        loginButton.setPermissions(Arrays.asList("email", "public_profile"));

        // Register callback
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Login Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Register receiver dynamically (lifecycle demo)
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);

        //Integrating firebase
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    Log.d("FCMGettingToken,", "Token: " + token);
                });
//cry8xLU-SrGoP249Jz0ZTq:APA91bFDjQ9JC_1f5sCEam9TaxotPPuaEvg1nFiKmM2Mer8TYS4prPfaGOXsHQQkNEFqBKGLBRYQxSVRkqU82JPh1ZEYAMOs3hdeh_KWQfasS7fXt5yiAKs
//AdMob app ID - ca-app-pub-3405371995301180~8977679883
// AdMob unit Id - ca-app-pub-3405371995301180/1483808029
        //device id B3765D134E2AB062B630581023C46F40
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        101
                );
            }
        }


        //widgets

        ScrollView scrollView = findViewById(R.id.scrollview);

// Scroll to top automatically when activity opens
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_UP));

        //spinner
        Spinner spinner = findViewById(R.id.spinner);
        String[] items = {"Java", "Kotlin", "Python"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);



        // textview
        TextView textView = findViewById(R.id.textview);
        textView.setText("Widget Form");
        textView.setTextColor(Color.BLUE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GridActivity.class);
                startActivity(intent);

            }
        });


        //Edittext
        EditText editText = findViewById(R.id.editText);
        String userInput = editText.getText().toString();
        editText.setHint("New hint");
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //button
        Button button = findViewById(R.id.button);
        // Handle button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hi!! all", Toast.LENGTH_SHORT).show();
                //swapping pages
                Intent intent = new Intent(MainActivity.this,RecyclerActivity.class);
                startActivity(intent);

            }
        });




        Button button1 = findViewById(R.id.connectivityBtn);
        // Handle button click
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ConnectivityActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Button av_btn = findViewById(R.id.audio_video_btn);
        // Handle button click
        av_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AudioVideoActivity.class);
                startActivity(intent);
                finish();

            }
        });


        Button fragment = findViewById(R.id.fragment);
        // Handle button click
        fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FragmentActivity.class);
                startActivity(intent);

            }
        });

        Button design_btn = findViewById(R.id.materialDesign);
        // Handle button click
        design_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MaterialDesignActivity.class);
                startActivity(intent);


            }
        });

        //checkbox
        CheckBox checkBox = findViewById(R.id.checkBox);
        boolean isChecked = checkBox.isChecked();
        Log.d("checkIngCheckBox",""+isChecked);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle checkbox state change
                Log.d("checkedState",""+isChecked);
                if(isChecked){

                }else{

                }
            }
        });



        //Radiobutton
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioOption1) {
                    Log.d("option1",""+"option1Selected");
                Toast.makeText(MainActivity.this,"Option1 is selected!!",Toast.LENGTH_SHORT).show();
                    // Option 1 selected
                } else if (checkedId == R.id.radioOption2) {
                    // Option 2 selected
                }else{

                }
            }
        });

        //switch
        Switch switchWidget = findViewById(R.id.switchWidget);
        boolean isSwitchOn = switchWidget.isChecked();
        Log.e("checkIngCheckBox",""+isSwitchOn);
        switchWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.e("onCheckedChanged","onCheckedChanged"+isChecked);
            }
        });

        //Imageview
        ImageView imageView = findViewById(R.id.imageView);
        //get image from drawable
        imageView.setImageResource(R.drawable.user);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //For API Integration Activity
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,APIIntegrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

// Load from URL using libraries like Glide or Picasso
        //image comes from api service
        Glide.with(this).load("https://www.w3schools.com/w3images/lights.jpg").into(imageView);

        //progressbar for determinate
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50);
        progressBar.setMax(100);

        //inderterminate
        ProgressBar indeterminateBar = findViewById(R.id.indeterminateProgressBar);
        indeterminateBar.setVisibility(View.VISIBLE); // Show loading
// indeterminateBar.setVisibility(View.GONE); // Hide when done

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // Handle home click
                    return true;
                } else if (id == R.id.nav_search) {
                    // Handle search click
                    return true;
                }
                return false;
            }
        });

        //Timepicker
        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                int hour =minute;
            }
        });

        //datepicker
        DatePicker datePicker = findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Months are 0-based
        int year = datePicker.getYear();

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Handle date change
                String currentDte = dayOfMonth +"-"+ (monthOfYear+1) +"-"+year;
                Toast.makeText(MainActivity.this, ""+currentDte, Toast.LENGTH_SHORT).show();

            }
        });



        //webview
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://en.wikipedia.org/wiki/Android_(operating_system)");

       // context() -getContext()
        //videoview
        VideoView videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(getApplicationContext());
        // if fragments used -> getContext()
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        String videoUrl = "https://www.html5rocks.com/en/tutorials/video/basics/devstories.webm";

        //fetch from gallery
        //Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_path);
        Uri videoUri = Uri.parse(videoUrl);
        videoView.setVideoURI(videoUri);
        videoView.start();


        //FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click
            }
        });

        Chip chip = findViewById(R.id.chip);
        chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle chip s+election
            }
        });

        Button btn = findViewById(R.id.intentBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IntentHandlingActivity.class);
                startActivity(intent);

            }
        });
        Button alertBtn = findViewById(R.id.alertBtn);
        alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for programmatic AlertDialog
                //createAlertDialog();

                //for layout
                createLayoutAlertDialog();
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();

            Toast.makeText(this, "Welcome " + personName + " (" + personEmail + ")", Toast.LENGTH_LONG).show();
            Log.d("GOOGLE", "Signed in as: " + personName + " " + personEmail);

        } catch (ApiException e) {
            Log.w("GOOGLE", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void getUserProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String name = object.getString("name");
                            String email = object.has("email") ? object.getString("email") : "No Email";
                            String id = object.getString("id");
                            String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=large";

                            Log.d("FB_USER", "Name: " + name + ", Email: " + email);
                            Toast.makeText(MainActivity.this, "Welcome " + name, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }



    @Override
    protected void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }
//
    private void createLayoutAlertDialog() {
        // Inflate custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView);
        builder.setTitle("Enter Details");

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            Toast.makeText(MainActivity.this,
                    "Name: " + name + "\nEmail: " + email,
                    Toast.LENGTH_LONG).show();
        });
        builder.setNegativeButton("Cancel ", (dialog, which) -> {
           dialog.dismiss();
        });
        builder.setNeutralButton("Ok",(dialog, which) ->{
           dialog.dismiss();
        });
        builder.create().show();
        }




    private void createAlertDialog() {
        // Create AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Exit App");
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(),"Alert Dialog Completed!!",Toast.LENGTH_SHORT).show();
                finish(); // close activity
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss(); // close dialog
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // close dialog
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
}

    @Override
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
        // Unregister to avoid memory leaks
        unregisterReceiver(networkReceiver);
    }
}

