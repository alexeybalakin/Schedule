package com.example.android.schedule.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.android.schedule.ScheduleApplication;
import com.example.android.schedule.R;
import com.example.android.schedule.adapters.StationListAdapter;
import com.example.android.schedule.model.Station;

import java.util.ArrayList;
import java.util.List;

public class StationSelectActivity extends AppCompatActivity {
    private RecyclerView rvStations;
    private StationListAdapter adapter;


    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_select);

        rvStations = findViewById(R.id.rvStations);
        rvStations.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StationListAdapter(selectStationList(), new StationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Station station) {
                context = StationSelectActivity.this;
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle("Информация о станции");
                String region = "";
                if (station.getRegionTitle() != null && !station.getRegionTitle().isEmpty())
                    region = "\n" + "Регион: " + station.getRegionTitle();
                String district = "";
                if (station.getDistrictTitle() != null && !station.getDistrictTitle().isEmpty())
                    region = "\n" + "Район: " + station.getDistrictTitle();
                ad.setMessage("Станция: " + station.getStationTitle()
                        + "\n" + "Страна: " + station.getCountryTitle()
                        + region
                        + district
                        + "\n" + "Город: " + station.getCityTitle()
                );
                ad.setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent();
                        intent.putExtra("stationName", station.getStationTitle());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                ad.setNegativeButton("Назад к списку", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                    }
                });
                ad.show();
            }
        });
        rvStations.setAdapter(adapter);

        final EditText etSearch = findViewById(R.id.etSearch);
        ImageButton ibSearch = findViewById(R.id.ibSearch);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = etSearch.getText().toString();
                searchStation(query);
            }
        });
    }


    //Выбираем какой список станций (отправления или назначения) будем использовать
    private List<Station> selectStationList() {
        List<Station> result = new ArrayList<>();
        if (getIntent().getExtras().getString("station").equals("departure")) {
            result = ScheduleApplication.departureStations;
        }
        if (getIntent().getExtras().getString("station").equals("destination")) {
            result = ScheduleApplication.destinationStations;
            ;
        }
        return result;
    }

    //Выполняем поиск по скиску станций
    private void searchStation(String query) {
        List<Station> temp = new ArrayList<>();
        for (Station station : selectStationList()) {
            if (station.getStationTitle().toLowerCase().contains(query.toLowerCase())) {
                temp.add(station);
            }
        }
        adapter.updateList(temp);
    }
}

