package com.example.agastya.morsecomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.*;
import org.opencv.android.OpenCVLoader;
public class MainActivity extends AppCompatActivity {
    Button sender,receiver;

    static {
        System.loadLibrary("opencv_java3");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sender=(Button)findViewById(R.id.btnsender);
        receiver=(Button)findViewById(R.id.btnrcv);

        if (!OpenCVLoader.initDebug()) {

            System.out.println("not working------------------");
        } else {

            System.out.println("work------------------");
        }

        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), sender.class);
                startActivity(i);
            }
        });

        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), receiver.class);
                startActivity(i);
            }
        });
    }
}
