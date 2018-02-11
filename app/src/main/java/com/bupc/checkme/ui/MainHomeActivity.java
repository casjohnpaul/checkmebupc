package com.bupc.checkme.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.bupc.checkme.R;
import com.bupc.checkme.app.CheckMeApplication;
import com.bupc.checkme.core.database.handler.DatabaseHandler;
import com.bupc.checkme.core.interfacez.OnMainNavigationObserver;
import com.bupc.checkme.ui.about.AboutFragment;
import com.bupc.checkme.ui.home.HomeFragment;

public class MainHomeActivity extends AppCompatActivity implements OnMainNavigationObserver {


    MediaPlayer player;

    private CheckMeApplication checkMeApplication;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    displayFragmentView(HomeFragment.newInstance());
                    return true;
                case R.id.navigation_help:
                    displayFragmentView(AboutFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        player = MediaPlayer.create(this, R.raw.background_sound);
        player.setLooping(true);
        player.start();

        initializeDatabase();

        displayFragmentView(HomeFragment.newInstance());

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bgMenuColor();
    }


    private void initializeDatabase() {
        checkMeApplication = CheckMeApplication.getInstance();

        DatabaseHandler databaseHelper = new DatabaseHandler(checkMeApplication.getApplicationContext());
        databaseHelper.initializeDatabase();
        databaseHelper.close();

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player = MediaPlayer.create(this, R.raw.background_sound);
            player.setLooping(true);
            player.start();
        }
    }


    @Override
    public void displayFragmentView(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
        ft.replace(R.id.flHomeContainer, fragment);
//        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void finishActivity() {

    }

    public void bgHomeColor() {
        navigation.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_main));
    }

    public void bgAboutColor() {
        navigation.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_about));
    }

    public void bgMenuColor() {
        navigation.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_menu));
    }

}
