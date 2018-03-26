package com.example.android.schedule;

import android.app.Application;
import com.example.android.schedule.model.Station;
import com.example.android.schedule.utils.JsonReader;
import java.util.List;


public class ScheduleApplication extends Application {

    public static List<Station> departureStations;
    public static List<Station> destinationStations;

    @Override
    public void onCreate() {
        super.onCreate();

        //В отдельном потоке разбираем файл с данными и получем из него списки станций
        new JsonReader(this).execute();
    }

}
