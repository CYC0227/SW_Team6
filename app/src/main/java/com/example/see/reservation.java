package com.example.see;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class reservation extends AppCompatActivity {

    RadioButton radioCal, radioTime, radioNum;
    Button btnMR;
    EditText numOfPeople;
    TimePicker timePicker;
    CalendarView calView;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMin, tvNum, tv_;
    int selectY, selectM, selectD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        btnMR = (Button) findViewById(R.id.btnEnd);

        numOfPeople = (EditText) findViewById(R.id.numPeople);

        radioCal = (RadioButton) findViewById(R.id.radioCal);
        radioTime = (RadioButton) findViewById(R.id.radioTime);
        radioNum = (RadioButton) findViewById(R.id.radioNum);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calView = (CalendarView) findViewById(R.id.calendarView);

        tv_ = (TextView) findViewById(R.id.tv_);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMin = (TextView) findViewById(R.id.tvMin);
        tvNum = (TextView) findViewById(R.id.tvNum);

        timePicker.setVisibility(View.INVISIBLE);
        calView.setVisibility(View.INVISIBLE);
        numOfPeople.setVisibility(View.INVISIBLE);
        tv_.setVisibility(View.INVISIBLE);

        btnMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvYear.setText(Integer.toString(selectY));
                tvMonth.setText(Integer.toString(selectM));
                tvDay.setText(Integer.toString(selectD));
                tvHour.setText(Integer.toString(timePicker.getCurrentHour()));
                tvMin.setText(Integer.toString(timePicker.getCurrentMinute()));
                String str = numOfPeople.getText().toString();
                tvNum.setText(str);
            }
        });

        radioCal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.INVISIBLE);
                calView.setVisibility(View.VISIBLE);
                numOfPeople.setVisibility(View.INVISIBLE);
                tv_.setVisibility(View.INVISIBLE);
            }
        });
        radioTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.VISIBLE);
                calView.setVisibility(View.INVISIBLE);
                numOfPeople.setVisibility(View.INVISIBLE);
                tv_.setVisibility(View.INVISIBLE);
            }
        });
        radioNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.INVISIBLE);
                calView.setVisibility(View.INVISIBLE);
                numOfPeople.setVisibility(View.VISIBLE);
                tv_.setVisibility(View.VISIBLE);
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