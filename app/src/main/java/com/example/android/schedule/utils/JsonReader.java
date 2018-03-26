package com.example.android.schedule.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.schedule.R;
import com.example.android.schedule.ScheduleApplication;
import com.example.android.schedule.model.City;
import com.example.android.schedule.model.Station;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonReader extends AsyncTask<Void, Void, Void> {
    private Context context;

    @Override
    protected Void doInBackground(Void... voids) {
        readJson();
        return null;
    }

    public JsonReader(Context context) {
        this.context = context;
    }

    private void readJson() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<City>> cities = new HashMap<>();
        try {
            cities = mapper.readValue(context.getResources().openRawResource(R.raw.allstations), new TypeReference<Map<String, List<City>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScheduleApplication.departureStations = getStationsFromCities(cities.get("citiesFrom"));
        ScheduleApplication.destinationStations = getStationsFromCities(cities.get("citiesTo"));
    }

    //Получаем список станций из списка городов
    private List<Station> getStationsFromCities(List<City> cities) {
        List<Station> stations = new ArrayList<>();
        for (City city : cities) {
            stations.addAll(city.getStations());
        }
        return stations;
    }
}
