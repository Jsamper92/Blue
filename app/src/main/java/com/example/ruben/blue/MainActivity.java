package com.example.ruben.blue;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private String mUsername, userEmail;
    TextView tvHeaderNombre, tvHeaderEmail;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth=null;
    private FirebaseUser mFirebaseUser=null;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     *
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // lanza SignUpActivity
            startActivity(new Intent(this, LogInActivity.class));
            finish();
            return;
        }
        mUsername = mFirebaseUser.getDisplayName();
        if(mUsername == null ){
            Intent intent = getIntent();
            mUsername = intent.getStringExtra(SignUpActivity.NOMUSU);
            userEmail = mFirebaseUser.getEmail();

        }else{
            mUsername = mFirebaseUser.getDisplayName();
            userEmail = mFirebaseUser.getEmail();
        }
        /*if (mFirebaseUser.getPhotoUrl() != null) {
             mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        }*/
        /*Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        cambiar color de las pestañas
        //tabLayout.setBackgroundColor(Color.parseColor("#FF5544"));

//        colocar iconos a las pestañas
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_publicar_viaje);
        /*tabLayout.getTabAt(1).setIcon(R.drawable.ic_menu_gallery);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_slideshow);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_menu_share);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        tvHeaderNombre = (TextView)header.findViewById(R.id.tvHeaderNombre);
        tvHeaderEmail = (TextView)header.findViewById(R.id.tvHeaderEmial);
        tvHeaderNombre.setText(mUsername);
        tvHeaderEmail.setText(userEmail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(item.getItemId()==R.id.action_settings){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.nav_slideshow) {
            mViewPager.setCurrentItem(2);
        } else if (id == R.id.nav_manage) {
            mViewPager.setCurrentItem(3);
        } else if (id == R.id.cerrarsesion) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1PublicarViaje publicar = new Tab1PublicarViaje();
                    return publicar;
                case 1:
                    Tab2BuscarViaje buscar = new Tab2BuscarViaje();
                    return buscar;
                case 2:
                    Tab3MisViajes misViajes = new Tab3MisViajes();
                    return misViajes;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "BUSCAR VIAJES";
                case 2:
                    return "MIS VIAJES";
            }
            return null;
        }
    }
}
