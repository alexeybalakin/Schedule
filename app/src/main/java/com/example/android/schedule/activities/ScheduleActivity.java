package com.example.android.schedule.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.schedule.R;

import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    private TextView tvDate;
    private EditText etDeparture;
    private EditText etDestination;
    final int REQUEST_CODE_DEPARTURE = 0;
    final int REQUEST_CODE_DESTINATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        tvDate = (TextView) findViewById(R.id.tvDate);
        etDeparture = (EditText) findViewById(R.id.etDeparture);
        etDestination = (EditText) findViewById(R.id.etDestination);
    }


    public void setDate(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                tvDate.setText(i2 +"."+i1+"."+i);
            }
        };
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSetListener,
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void setDeparture(View view) {
        Intent intent = new Intent(this, StationSelectActivity.class);
        intent.putExtra("station", "departure");
        startActivityForResult(intent, REQUEST_CODE_DEPARTURE);

    }

    public void setDestination(View view) {
        Intent intent = new Intent(this, StationSelectActivity.class);
        intent.putExtra("station", "destination");
        startActivityForResult(intent, REQUEST_CODE_DESTINATION);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_DEPARTURE:
                    etDeparture.setText(data.getStringExtra("stationName"));
                    break;
                case REQUEST_CODE_DESTINATION:
                    etDestination.setText(data.getStringExtra("stationName"));
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.schedule:
                startActivity(new Intent(this, ScheduleActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
