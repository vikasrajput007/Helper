package com.example.helper;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.utils.MySharedData;

public class Home_Screen_Vendor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Vendor_Home.OnFragmentInteractionListener,
        Vendor_History_Fragment.OnFragmentInteractionListener, Vendor_Profile_Fragment.OnFragmentInteractionListener, Vendor_Payment.OnFragmentInteractionListener {

    TextView vendor_name, vendor_no, helper_name;;
    String name, mobileno, address, profile_pic,user_mobile;
    ImageView edit_profile, vendorImage;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("helper");
//        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        helper_name = findViewById(R.id.helper_name);
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        vendor_name = header.findViewById(R.id.vendorname);
        vendor_no = header.findViewById(R.id.vendor_contactNo);
        edit_profile = header.findViewById(R.id.edit);
        vendorImage = header.findViewById(R.id.vendorimage);
        profile_pic = MySharedData.getGeneralSaveSession("vendor_image");
        byte[] imageAsBytes = Base64.decode(profile_pic.getBytes(), Base64.DEFAULT);
        vendorImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        name = MySharedData.getGeneralSaveSession("vendor_name");
        mobileno = MySharedData.getGeneralSaveSession("vendor_Co_no");
        vendor_no.setText("" + mobileno);
        vendor_name.setText("" + name);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/caponelight.ttf");
        helper_name.setTypeface(custom_font);

        Vendor_Home vendor_home = new Vendor_Home();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.vendor_frame, vendor_home);
        fragmentTransaction.commit();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {


              name = intent.getStringExtra("name");
             address = intent.getStringExtra("address");
              user_mobile = intent.getStringExtra("mobileno");


            if (name != null) {
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("address",address);
                bundle.putString("mobileno",user_mobile);
                Fragment fragment = new Vendor_Home();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.vendor_frame, fragment).addToBackStack(null).commit();

            }

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =   findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id==R.id.edit)
        {
            Toast.makeText(Home_Screen_Vendor.this, "Click", Toast.LENGTH_SHORT).show();
            Vendor_Profile_Fragment profile_fragment= new Vendor_Profile_Fragment();
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.vendor_frame,profile_fragment);
            fragmentTransaction.commit();
        }


        if (id == R.id.vender_home)
        {
            Vendor_Home vendor_home= new Vendor_Home();
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.vendor_frame,vendor_home);
            fragmentTransaction.commit();


        }
        else if (id == R.id.vender_profile)
        {
            Vendor_Profile_Fragment vendor_profile= new Vendor_Profile_Fragment();
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.vendor_frame,vendor_profile);
            fragmentTransaction.commit();

        }
        else if (id == R.id.vender_history)
        {
            Vendor_History_Fragment vendor_history= new Vendor_History_Fragment();
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.vendor_frame,vendor_history);
            fragmentTransaction.commit();
        }
        else if (id == R.id.vender_payment)
        {
            Vendor_Payment vendor_payment= new Vendor_Payment();
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.vendor_frame,vendor_payment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.vender_review)
        {

        }
        else if (id == R.id.vender_logout)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

}
