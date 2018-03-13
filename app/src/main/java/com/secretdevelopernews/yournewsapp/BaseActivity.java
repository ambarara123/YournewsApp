package com.secretdevelopernews.yournewsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout fullView;
    NavigationView navigationView;
   // public String linkUrl="https://newsapi.org/v2/top-headlines?country=in&apiKey=646fffb9629a40e5a5d099caedd4a572";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);



    }

    @Override
    public void setContentView(int layoutResID)
    {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        navigationView = (NavigationView) fullView.findViewById(R.id.navigationView);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (useToolbar())
        {
            setSupportActionBar(toolbar);
            setTitle(getTitle().toString());
        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }

        if ( !useNavDrawer() ) {
            fullView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                fullView,toolbar,R.string.drawer_open,R.string.drawer_close);

        fullView.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    //for overriding toolbar and navigation bar
    protected boolean useToolbar()
    {
        return true;
    }

    public boolean useNavDrawer(){
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        closeDrawer();

        switch (id){
            case R.id.sports:
                startActivity(new Intent(this,SportActivity.class));
                break;
            case R.id.enter:
                startActivity(new Intent(this,EntertainmentActivity.class));
                break;

            case R.id.science:
                startActivity(new Intent(this,ScienceActivity.class));
                break;
            case R.id.technology:
                startActivity(new Intent(this,TechnologyActivity.class));
                break;
            case R.id.health:
                startActivity(new Intent(this,HealthActivity.class));
                break;
            case R.id.business:
                startActivity(new Intent(this,BusinessActivity.class));
                break;
            case R.id.inter:
                startActivity(new Intent(this,InternationalActivity.class));
                break;
        }

        return false;
    }

    private void closeDrawer() {
        fullView.closeDrawer(GravityCompat.START);
    }

    private void openDrawer(){
        fullView.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {

        if (fullView.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}