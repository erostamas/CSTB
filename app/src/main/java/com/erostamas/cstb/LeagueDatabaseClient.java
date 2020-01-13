package com.erostamas.cstb;

import android.os.AsyncTask;
import android.util.Log;

import com.erostamas.cstb.ui.main.FilteredGameListFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LeagueDatabaseClient extends AsyncTask<Void, Integer, LeagueDataBase> {

    private LeagueDataBase _dataBase;
    //private RedisResponseUser _redisResponseUser;

    public LeagueDatabaseClient(LeagueDataBase dataBase/*, RedisResponseUser redisResponseUser*/) {
        _dataBase = dataBase;
        //_redisResponseUser = redisResponseUser;
    }

    @Override
    protected LeagueDataBase doInBackground(Void... params) {
        LeagueDataBase data = new LeagueDataBase();
        try {
            Document doc = Jsoup.connect("http://www.bajnoksagok.hu/2057/sorsolas/T%C3%A9li+Bajnoks%C3%A1g+2020+-+Kedd-1?fbclid=IwAR0MUHGlAmrn4gZfsL5GaH-DZHdhVUbJhi_zsbGdAX4Z66ei9f-aiAoqffM").get();
            for (Element table : doc.select(".md-list-2")) {
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    if (cols.get(2).text().contains("CSTB")) {
                        String result = cols.get(3).text().replace("\u00a0", "");
                        String home = cols.get(2).text().split(" - ")[0];
                        String away = cols.get(2).text().split(" - ")[1];
                        String dateTime = cols.get(0).text() + " " + cols.get(1).text();
                        //Log.i("erostamasdebug", dateTime);
                        data.addGame(new Game(home, away, result, dateTime));
                    }
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
        FilteredGameListFragment.adapter.notifyDataSetChanged();
        Log.i("erostamasdebug", "ok");
    }
}

