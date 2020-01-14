package com.erostamas.cstb;

import android.os.AsyncTask;
import android.util.Log;

import com.erostamas.cstb.ui.main.GameListFragment;
import com.erostamas.cstb.ui.main.SectionsPagerAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LeagueDatabaseClient extends AsyncTask<Void, Integer, LeagueDataBase> {

    private LeagueDataBase _dataBase;
    private String _leagueDatabaseUrl;
    private String _myTeam;
    private SectionsPagerAdapter _sectionsPagerAdapter;

    public LeagueDatabaseClient(LeagueDataBase dataBase, String leagueDatabaseUrl, String myTeam, SectionsPagerAdapter sectionsPagerAdapter) {
        _dataBase = dataBase;
        _leagueDatabaseUrl = leagueDatabaseUrl;
        _myTeam = myTeam;
        _sectionsPagerAdapter = sectionsPagerAdapter;
    }

    @Override
    protected LeagueDataBase doInBackground(Void... params) {
        LeagueDataBase data = new LeagueDataBase();
        try {
            Document doc = Jsoup.connect(_leagueDatabaseUrl).get();
            for (Element table : doc.select(".md-list-2")) {
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    String result = cols.get(3).text().replace("\u00a0", "");
                    String home = cols.get(2).text().split(" - ")[0];
                    String away = cols.get(2).text().split(" - ")[1];
                    String dateTime = cols.get(0).text() + " " + cols.get(1).text();
                    //Log.i("erostamasdebug", dateTime);
                    data.addGame(new Game(home, away, result, dateTime));
                }
            }
        } catch (IOException e) {}
        return data;
    }

    @Override
    protected void onPostExecute(LeagueDataBase dataBase) {
        //Log.i("erostamasdebug", "onPostExecute");
        _dataBase.clear();
        for(int i = 0; i < dataBase.getGames().size(); i++){
            _dataBase.addGame(dataBase.getGames().get(i));

        }
        _sectionsPagerAdapter.notifyFragments();
        Log.i("erostamasdebug", "ok");
    }
}

