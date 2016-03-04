package com.dp.hellowife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.dp.hellowife.login.EnterPin;
import com.dp.hellowife.login.SetUserPin;
import com.dp.hellowife.notes.NotesList;
import com.msf.pinlibrary.PrefHelper;

/**
 * Created by akshayas on 1/29/2016.
 */
public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    SharedPreferences pinLockPrefs;
    FloatingActionButton ring;
    LinearLayout memoriesLayout, notesLayout, greetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        setUpViews();
        setUpListeners();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setUpViews() {
        pinLockPrefs = PrefHelper.getPref(this.getApplicationContext(), "PinLockPrefs");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        ring = (FloatingActionButton) findViewById(R.id.ring);

        memoriesLayout = (LinearLayout) findViewById(R.id.memories_layout);
        notesLayout = (LinearLayout) findViewById(R.id.notes_layout);
        greetLayout = (LinearLayout) findViewById(R.id.greet_layout);
    }

    private void setUpListeners() {
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ring.setOnClickListener(this);
        memoriesLayout.setOnClickListener(this);
        notesLayout.setOnClickListener(this);
        greetLayout.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
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
    public boolean onNavigationItemSelected(MenuItem item) {

        selectDrawerItem(item.getItemId());
        item.setChecked(true);
        setTitle(item.getTitle());
        drawer.closeDrawers();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectDrawerItem(int itemId) {
        // Create a new fragment and specify the planet to show based on
        // position
//        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_camera:
                // Handle the camera action
                break;
            case R.id.nav_gallery:
                // Handle the gallery action
                break;
            case R.id.nav_slideshow:
                //Handle the slideshow action
                break;
            case R.id.nav_manage:
                //Handle the manage action
                break;
            case R.id.nav_share:
                //Handle the share action
                break;
            case R.id.nav_send:
                //Handle the send action
                break;
            default:
                break;
        }

        /*try {
            fragment = HomeFragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();*/

        // Highlight the selected item, update the title, and close the drawer

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.ring:
                if (pinLockPrefs.getBoolean("isPinSet", false)) {
                    Intent intent = new Intent(this, EnterPin.class);
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_CONFIRM_PIN);
                } else {
                    Intent intent = new Intent(this, SetUserPin.class);
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_SET_PIN);
                }
                break;

            case R.id.memories_layout:
                break;

            case R.id.notes_layout:
                Intent intent = new Intent(this, NotesList.class);
                startActivity(intent);
//                Toast.makeText(this, "My Notes on her...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.greet_layout:
                break;
        }
    }
}
