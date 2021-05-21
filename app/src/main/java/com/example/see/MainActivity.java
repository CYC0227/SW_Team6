package com.example.see;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU=101;
    String userid="1111";
    String userpw="1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_MENU){
            if(resultCode==RESULT_OK){
                String menu=data.getExtras().getString("menu");
                Toast.makeText(getApplicationContext(), "응답으로 전달된 menu : "+menu, Toast.LENGTH_LONG).show();
            }
        }
    }
    public void login(View v){
        EditText id=(EditText)findViewById(R.id.eid);
        EditText pw=(EditText)findViewById(R.id.epw);

        if(id.getText().toString().equals(userid)){
            if(pw.getText().toString().equals(userpw)){
                Intent intent=new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(intent,REQUEST_CODE_MENU);
            }
            else Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(MainActivity.this, "로그인실패", Toast.LENGTH_LONG).show();
    }
}