package com.example.see;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class reservation extends AppCompatActivity {

    RadioButton radioCal, radioTime;
    Button makeReservation;
    TimePicker timePicker;
    CalendarView calView;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMin;
    int selectY, selectM, selectD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        makeReservation = (Button) findViewById(R.id.btnEnd);

        radioCal = (RadioButton) findViewById(R.id.radioCal);
        radioTime = (RadioButton) findViewById(R.id.radioTime);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calView = (CalendarView) findViewById(R.id.calendarView);

        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMin = (TextView) findViewById(R.id.tvMin);

        timePicker.setVisibility(View.INVISIBLE);
        calView.setVisibility(View.INVISIBLE);

        makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvYear.setText(Integer.toString(selectY));
                tvMonth.setText(Integer.toString(selectM));
                tvDay.setText(Integer.toString(selectD));
                tvHour.setText(Integer.toString(timePicker.getCurrentHour()));
                tvMin.setText(Integer.toString(timePicker.getCurrentMinute()));
            }
        });

        radioCal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.INVISIBLE);
                calView.setVisibility(View.VISIBLE);
            }
        });
        radioTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.VISIBLE);
                calView.setVisibility(View.INVISIBLE);
            }
        });

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectY = year;
                selectM = month + 1;
                selectD = dayOfMonth;
            }
        });
    }
}
