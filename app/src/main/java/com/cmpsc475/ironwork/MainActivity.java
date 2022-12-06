package com.cmpsc475.ironwork;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication
    Button logout;

    private  Toolbar myToolbar;
    private DrawerLayout myDrawerLayout;
    private BottomNavigationView bottomNavigationView;
    private JSONObject json;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    AppDatabase db;
    JobDao jobDao;
    static JobViewModel jobViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SearchFragment()).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).commit();
                        return true;
                }
                return false;
            }

        });


//        db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "jobs").allowMainThreadQueries().build();
//
//        jobDao = db.jobDao();


        initJobViewModel();



        String json = loadJSONFromAsset();
        try {
            JSONArray m_jArry = new JSONArray(json);
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int id = jo_inside.getInt("jobID");
                String title = jo_inside.getString("jobTitle");
                String description = jo_inside.getString("jobDescription");
                String location = jo_inside.getString("jobLocation");
                String pay = jo_inside.getString("jobPay");
                String date = jo_inside.getString("jobDate");
                String time = jo_inside.getString("jobTime");
                String duration = jo_inside.getString("jobDuration");
                String contact = jo_inside.getString("jobContact");
                String phone = jo_inside.getString("jobContactPhone");
                String email = jo_inside.getString("jobContactEmail");
                jobViewModel.insert(new Job(id, title, description, location, pay, date, time, duration, contact, phone, email));
                Log.d("JSON", "onCreate: " + id + " " + title + " " + description + " " + location + " " + pay + " " + date + " " + time + " " + duration + " " + contact + " " + phone + " " + email);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static JobViewModel getJobViewModel() {
        return jobViewModel;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.searchBtn:
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SearchFragment()).commit();
            return(true);
        case R.id.reset:
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
            return(true);
        case R.id.about:
            return(true);
        case R.id.logout:
            FirebaseAuth.getInstance().signOut();
            //After logging out send user to Login Activity to login again
            sendToLoginActivity();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check if user has already signed in if not send user to Login Activity
        if(currentUser==null)
        {
            sendToLoginActivity();
        }
    }

    private void sendToLoginActivity() {
        //To send user to Login Activity
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("jobs.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void initJobViewModel() {
        jobViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(JobViewModel.class);
    }



}