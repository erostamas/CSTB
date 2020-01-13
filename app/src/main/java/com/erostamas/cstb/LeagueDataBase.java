package com.erostamas.cstb;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;

public class LeagueDataBase {
    private ArrayList<Game> _games = new ArrayList<>();

    public ArrayList<Game> getGames() { return _games; }

    public void clear() {
        Log.i("erostamasdebug", "clearing database");
        _games.clear();
    }

    public void addGame(Game game) {
        _games.add(game);
        Log.i("erostamasdebug", "added game to database: " + game._dateTime);
        _games.sort(new Comparator<Game>() {
            public int compare(Game game1, Game game2) {
                return game1._dateTime.compareTo(game2._dateTime);
            }
        });
    }
}
