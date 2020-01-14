package com.erostamas.cstb.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.erostamas.cstb.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    private String _leagueDatabaseUrl;
    private String _myTeam;

    // Fragments
    private GameListFragment allGamesListFragment = GameListFragment.newInstance(0);
    private GameListFragment myGamesListFragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String leagueDatabaseUrl, String myTeam) {
        super(fm);
        mContext = context;
        _leagueDatabaseUrl = leagueDatabaseUrl;
        _myTeam = myTeam;
        myGamesListFragment = GameListFragment.newInstance(1, _myTeam);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return myGamesListFragment;
        } else {
            return allGamesListFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    public void notifyFragments() {
        allGamesListFragment.adapter.notifyDataSetChanged();
        myGamesListFragment.adapter.notifyDataSetChanged();
    }
}