package com.example.testapplication.MainActiviy_Screen;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.example.testapplication.R;

/**
 * Created by 한국정보기술 on 2016-08-18.
 */
public class asdwqdmm extends AsyncTask<Void, Void, Void> {
    Activity activity;
    Toolbar toolbar;

    public asdwqdmm(Toolbar toolbar, Activity activity)
    {
        this.activity = activity;
        this.toolbar = toolbar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected Void doInBackground(Void... voids) {



        return null;
    }
}
