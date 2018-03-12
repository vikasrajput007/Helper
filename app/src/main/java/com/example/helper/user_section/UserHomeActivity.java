package com.example.helper.user_section;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helper.R;
import com.example.helper.user_section.fragments.UserHomeFragment;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView helper_name;
    EditText search_field;
    UserHomeFragment userHomeFragment;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        initView();
    }

    private void initView() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            helper_name = findViewById(R.id.helper_name);
            search_field = findViewById(R.id.search_field);

            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/caponelight.ttf");
            helper_name.setTypeface(custom_font);

            if(userHomeFragment==null){
                fragmentManager = getSupportFragmentManager();
                userHomeFragment = new UserHomeFragment();
                fragmentTransaction =  fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container_layout,userHomeFragment);
                fragmentTransaction.commit();
            }

            else{
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction =  fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container_layout,userHomeFragment);
                fragmentTransaction.commit();
            }

        } catch (Exception e) {

        }

    }


    @Override
    public void onBackPressed() {

        int back_stack = fragmentManager.getBackStackEntryCount();


//        for(int i =0;i<=back_stack;i++){
//            FragmentManager.BackStackEntry backEntry = (FragmentManager.BackStackEntry) fragmentManager.getBackStackEntryAt(i);
//            String tag = backEntry.getName();
//
//            System.out.println("what is tag name :"+tag);
//        }
        
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

//        int back_stack_entry =   fragmentManager.getBackStackEntryCount();

       else if(fragmentManager.getBackStackEntryCount()==1){

//            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

            if(helper_name.getVisibility() == View.GONE){

                helper_name.setVisibility(View.VISIBLE);
                search_field.setVisibility(View.VISIBLE);
            }
           // for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
           // }

            Toast.makeText(this, "This is back pressed Toast", Toast.LENGTH_SHORT).show();
//            super.onBackPressed();
        }
//        else if(fragmentManager.getBackStackEntryCount()==0) {
//
//        }

//        else if(back_stack!=0){
//
//            Toast.makeText(this, "Back stack @ counter :"+back_stack, Toast.LENGTH_SHORT).show();
//
//        }

        else {
            Toast.makeText(this, "This is back pressed on super Toast", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wallet) {
            // Handle the camera action
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_register) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
