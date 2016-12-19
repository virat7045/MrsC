package com.example.agastya.morsecomm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

/**
 * Created by Agastya on 8/23/2016.
 */
public class sender extends ActionBarActivity {
    Button send;
    EditText text;
    Camera camera;
    int[][] mar={{1,3},{3,1,1,1},{3,1,3,1},{3,1,1},{1},{1,1,3,1},{3,3,1},{1,1,1,1},{1,1},{1,3,3,3},{3,1,3},{1,3,1,1},{3,3},{3,1},{3,3,3},{1,3,3,1},{3,3,1,3},{1,3,1},{1,1,1},{3},{1,1,3},{1,1,1,3},{1,3,3},{3,1,1,3},{3,1,3,3},{3,3,1,1}};
    char[] car={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender);

        send=(Button)findViewById(R.id.btnsnd);

        text=(EditText) findViewById(R.id.etmsg);




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg=text.getText().toString().toLowerCase();
                if(msg.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"enter message first",Toast.LENGTH_LONG).show();
                }
                else
                {
                    send.setEnabled(false);

                    camera=camera.open();
                    Parameters p=camera.getParameters();
//                    p.setFlashMode(p.FLASH_MODE_TORCH);
//                    camera.setParameters(p);
//                    camera.startPreview();

                    for(int i=0;i<msg.length();i++)
                    {
                        char c=msg.charAt(i);
                        if(c==' ')
                        {
                            try {
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            for (int j = 0; j < car.length; j++) {
                                if (c == car[j]) {
                                    for (int k = 0; k < mar[j].length; k++) {
                                        p.setFlashMode(p.FLASH_MODE_TORCH);
                                        camera.setParameters(p);
                                        camera.startPreview();
                                        int time = mar[j][k];
                                        try {
                                            Thread.sleep(100 * time);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        p.setFlashMode(p.FLASH_MODE_OFF);
                                        camera.setParameters(p);
                                        camera.startPreview();
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                }
                            }
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    send.setEnabled(true);

                   camera.stopPreview();
                    camera.release();



                }


            }
        });
    }

}
