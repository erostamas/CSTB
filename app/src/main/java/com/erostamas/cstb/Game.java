package com.erostamas.cstb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {
    public String _home;
    public String _away;
    public String _result;
    public Date _dateTime;

    Game(String home, String away, String result, String dateTime) {
        _home = home.toUpperCase();
        _away = away.toUpperCase();
        _result = result;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            _dateTime = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
