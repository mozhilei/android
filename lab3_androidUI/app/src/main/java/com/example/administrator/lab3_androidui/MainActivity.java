package com.example.administrator.lab3_androidui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.linearlayout);
//        setContentView(R.layout.relative);
        fun1();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            setContentView(R.layout.activity_main);
            fun1();
            return true;
        }
            return super.onKeyDown(keyCode, event);
    }

    public void fun1()
    {
        Button b1=findViewById(R.id.button1);
        Button b2=findViewById(R.id.button2);
        Button b3=findViewById(R.id.button3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.linearlayout);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.relative);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.tablelayout);
            }
        });
    }
}
