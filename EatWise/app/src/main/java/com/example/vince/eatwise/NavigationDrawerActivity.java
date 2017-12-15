package com.example.vince.eatwise;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vince.eatwise.QueryData.QueryFilter;
import com.example.vince.eatwise.Utility.LoginInfo;
import com.example.vince.eatwise.Utility.Registration;
import com.example.vince.eatwise.Utility.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    /**
     * Find views that buttons correspond to
     * @param savedInstanceState Instance State to operate upon
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        // When activity is initiated, switch to RecommendFragment as default entry fragment.
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new RecommendFragment()).commit();

        // Get firebase realtime database reference and authentication service
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView nameView = (TextView)header.findViewById(R.id.profile_name);
        TextView emailView = (TextView)header.findViewById(R.id.profile_email);
        nameView.setText(mAuth.getCurrentUser().getDisplayName());
        emailView.setText(mAuth.getCurrentUser().getEmail());

        // TODO: use user's uid as unique identifier in the database
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    /**
     *  Handle navigation view item clicks here.
     *  @param item MenuItem: The item associated with the clicked button
     *  @return Boolean: always return true
     */
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recommendation) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new RecommendFragment()).commit();
        } else if (id == R.id.nav_search) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SearchFragment()).commit();
        } else if (id == R.id.nav_signout) {
            mAuth.signOut();
            Intent intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
