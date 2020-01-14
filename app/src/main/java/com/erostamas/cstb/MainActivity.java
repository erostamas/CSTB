package com.erostamas.cstb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.erostamas.cstb.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public LeagueDataBase leagueDataBase = new LeagueDataBase();
    private String leagueDatabaseUrl = "http://www.bajnoksagok.hu/2057/sorsolas/T%C3%A9li+Bajnoks%C3%A1g+2020+-+Kedd-1?fbclid=IwAR0MUHGlAmrn4gZfsL5GaH-DZHdhVUbJhi_zsbGdAX4Z66ei9f-aiAoqffM";
    private String myTeam = "CSTB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), leagueDatabaseUrl, myTeam);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        LeagueDatabaseClient client = new LeagueDatabaseClient(leagueDataBase, leagueDatabaseUrl, myTeam, sectionsPagerAdapter);
        client.execute();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}