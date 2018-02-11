package com.bupc.checkme.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.bupc.checkme.R;
import com.bupc.checkme.app.CheckMeApplication;
import com.bupc.checkme.core.database.handler.DatabaseHandler;
import com.bupc.checkme.core.interfacez.OnMainNavigationObserver;
import com.bupc.checkme.ui.base.BaseActivity;
import com.bupc.checkme.ui.category.CategoryFragment;
import com.bupc.checkme.ui.home.HomeFragment;

import java.io.File;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnMainNavigationObserver {


    private CheckMeApplication checkMeApplication;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initializeDatabase();
        displayView();
    }

    @Override
    public void onBackPressed() {

    }

    private void initializeDatabase() {
        checkMeApplication = CheckMeApplication.getInstance();

        DatabaseHandler databaseHelper = new DatabaseHandler(checkMeApplication.getApplicationContext());
        databaseHelper.initializeDatabase();
        databaseHelper.close();

    }

    private void displayView() {
        displayFragmentView(CategoryFragment.newInstance());
    }

    @Override
    public void displayFragmentView(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
        ft.replace(R.id.flContainer, fragment);
//        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void finishActivity() {
        finish();
    }

}
