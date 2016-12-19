package com.example.agastya.morsecomm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Agastya on 10/17/2016.
 */
public class output extends ActionBarActivity {
    TextView opstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);

        opstring=(TextView)findViewById(R.id.optext);

        Intent i=getIntent();
        String str=i.getExtras().getString("output");
        opstring.setText(str);
    }
}
