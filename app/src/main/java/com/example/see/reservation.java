package com.example.see;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class reservation extends AppCompatActivity {

    RadioButton radioCal, radioTime;
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
//        radioNum = (RadioButton) findViewById(R.id.radioNum);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calView = (CalendarView) findViewById(R.id.calendarView);

        tv_ = (TextView) findViewById(R.id.tv_);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMin = (TextView) findViewById(R.id.tvMin);
        tvNum = (TextView) findViewById(R.id.tvNum);

        tvNum.setText(getIntent().getStringExtra("INPUT_NUMBER"));

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
//                String str = numOfPeople.getText().toString();
//                tvNum.setText(str);

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(reservation.this);
                alertDialogBuilder.setMessage("예약하시겠습니까?\n" +
                        Integer.toString(selectY) + "/" +
                        Integer.toString(selectM) + "/" +
                        Integer.toString(selectD) + " " +
                        Integer.toString(timePicker.getCurrentHour()) + ":" +
                        Integer.toString(timePicker.getCurrentMinute()) + " " +
                        getIntent().getStringExtra("INPUT_NUMBER") + "명")
                        .setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(reservation.this, "예약이 완료되었습니다.",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent();
                                        intent.putExtra("INPUT_TEXT",
                                                Integer.toString(selectY) + "/" +
                                                        Integer.toString(selectM) + "/" +
                                                        Integer.toString(selectD) + " " +
                                                        Integer.toString(timePicker.getCurrentHour()) + ":" +
                                                        Integer.toString(timePicker.getCurrentMinute()) + " " +
                                                        getIntent().getStringExtra("INPUT_NUMBER") + "명");
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(reservation.this, "예약이 취소되었습니다.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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
//        radioNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timePicker.setVisibility(View.INVISIBLE);
//                calView.setVisibility(View.INVISIBLE);
//                numOfPeople.setVisibility(View.VISIBLE);
//                tv_.setVisibility(View.VISIBLE);
//            }
//        });

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