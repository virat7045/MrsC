package com.example.agastya.morsecomm;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.nfc.Tag;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class receiver extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 ,View.OnTouchListener{
    private CameraBridgeViewBase jcamera;
    static int[][] mar={{1,3},{3,1,1,1},{3,1,3,1},{3,1,1},{1},{1,1,3,1},{3,3,1},{1,1,1,1},{1,1},{1,3,3,3},{3,1,3},{1,3,1,1},{3,3},{3,1},{3,3,3},{1,3,3,1},{3,3,1,3},{1,3,1},{1,1,1},{3},{1,1,3},{1,1,1,3},{1,3,3},{3,1,1,3},{3,1,3,3},{3,3,1,1}};
    static char[] car={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    static int[] temp=new int[4];
    static int index=0;
    static int l,m;
    static long start_on,start_off,off_interval,on_interval;
    static boolean state_flag=false,first_flag=true;
    static String opstr="";
    private  BaseLoaderCallback mbase = new BaseLoaderCallback(this) {
     @Override
     public void onManagerConnected(int status) {
         switch (status) {
             case LoaderCallbackInterface.SUCCESS:
             {

                jcamera.enableView();
             }
             default:
                 super.onManagerConnected(status);
         }
     }
 };


   // private JavaCameraView jcamera;
    private Double[] h=new Double[5];
    private Double[] k=new Double[5];
    private double x=0;
    private double y=0;
int i=0;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        jcamera = (CameraBridgeViewBase)findViewById(R.id.cameraview);
        jcamera.setVisibility(CameraBridgeViewBase.VISIBLE);
        jcamera.setCvCameraViewListener(this);


    }
    private static final String TAG="-----------------";
    boolean  touched=false;
    private Mat mRgba;
    private List<Rect> ListOfRect = new ArrayList<Rect>();



  /*  @Override
    public boolean onTouch(View arg0, MotionEvent event) {

        double cols = mRgba.cols();
        double rows = mRgba.rows();

        double xOffset = (jcamera.getWidth() - cols) / 2;
        double yOffset = (jcamera.getHeight() - rows) / 2;

        h[i] = (double)(event).getX() - xOffset;
        k[i] = (double)(event).getY() - yOffset;

        h[i]=x;
        k[i]=y;

        ListOfRect.add(new Rect(x-100, y-100, x+100, y+100));

        Log.i(TAG, "Touch image coordinates: (" + h[i] + ", " + k[i] + ")");

        i++;

        return true;// don't need subsequent touch events
    }*/

    public void onResume()
{
    super.onResume();
    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0,this,mbase);
}

public void onDestroy()
{
    super.onDestroy();
    if(jcamera != null)
    {
        jcamera.disableView();
    }

}
    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRgba=inputFrame.rgba();
        Double r=0.0,g=0.0,b=0.0;
      //  Log.e(TAG, "Sum of onCameraFrame: "+Core.sumElems(mRgba) );

        jcamera.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double cols = mRgba.cols();
                double rows = mRgba.rows();
                double xOffset = (jcamera.getWidth() - cols) / 2;
                double yOffset = (jcamera.getHeight() - rows) / 2;
                h[i] = (double)(event).getX() - xOffset;
                k[i] = (double)(event).getY() - yOffset;

                x= h[i];
                y=k[i];
                String str="focus changed";
                Toast.makeText(getBaseContext(),Html.fromHtml("<font color='#ff0000' ><b> "+ str +"</b></font>"),Toast.LENGTH_SHORT).show();
                ListOfRect.add(new Rect(x, y, 80, 100));

              //  Log.i(TAG, "Touch image coordinates: (" + h[i] + ", " + k[i] + ")");
              //  Imgproc.rectangle(mRgba, ListOfRect.get(i).tl(), ListOfRect.get(i).br(),new Scalar( 0,0,255 ),0,8,0 );
             //   i++;

                return false;// don't need subsequent touch events

            }
        });
//Out of Touch

        try {
            for(int i=0; i<ListOfRect.size(); i++){
             //   System.out.println("i value: "+i);
               // Imgproc.rectangle(mRgba, ListOfRect.get(i).tl(), ListOfRect.get(i).br(),new Scalar( 255,0,0 ),0,8,0 );
                Imgproc.rectangle(mRgba,new Point(x,y),new Point(x + 100,y+100),new Scalar( 0,0,255 ),0,8,0);}
                Mat mzoomwindow = mRgba.submat(new Rect(x,y,100,100));
         /* Mat  mSepiaKernel = new Mat(4, 4, CvType.CV_32F);
            mSepiaKernel.put(0, 0,  0.189f, 0.769f, 0.393f, 0f);
            mSepiaKernel.put(1, 0,   0.168f, 0.686f, 0.349f, 0f);
            mSepiaKernel.put(2, 0,   0.131f, 0.534f, 0.272f, 0f);
            mSepiaKernel.put(3, 0,   0.000f, 0.000f, 0.000f, 1f);
                Core.transform(mzoomwindow,mzoomwindow,mSepiaKernel);*/
             //   Log.i(TAG,"Sub Mat:"+Core.sumElems(mzoomwindow));

            for (int i = 0; i <mzoomwindow.cols() ; i++) {
                for (int j = 0; j < mzoomwindow.rows(); j++) {
                    double[] rgb = mzoomwindow.get(i,j);
                  //  Log.i("", "red:"+rgb[0]+"  green:"+rgb[1]+"  blue:"+rgb[2]);
                    r=r+rgb[0];
                    g=g+rgb[1];
                    b=b+rgb[2];

                }
            }
                r=r/(mzoomwindow.cols()*mzoomwindow.rows());
                g=g/(mzoomwindow.cols()*mzoomwindow.rows());
                b=b/(mzoomwindow.cols()*mzoomwindow.rows());
          //  Log.e(TAG, "-------------------RGB final "+"r="+r+ "g="+g+"b="+b );
                    if(r>=210 && g>=210 && b>= 210)
                    {

                        if(state_flag==false)
                        {
                            start_on=System.currentTimeMillis();
                            state_flag=true;
                            off_interval=start_on-start_off;
                            Log.e(TAG,"off interval="+off_interval);
                            if(off_interval>1000 && off_interval<2300)
                            {
                                //index=0;

                                for(l=0;l<mar.length;l++)
                                {

                                    if(mar[l].length==index) {

                                        for (m = 0; m < index; m++) {
                                            Log.e(TAG,"temp["+m+"]="+temp[m]);
                                            Log.e(TAG,"mar["+m+"]="+mar[l][m]);
                                            if (mar[l][m] != temp[m]) {
                                                Log.e(TAG,"abt to break inner ");
                                                break;

                                            }
                                        }
                                        Log.e(TAG,"first for loop broken ");
                                        if(m==index)
                                        {
                                            Log.e(TAG,"abt to break outer ");
                                            opstr=opstr+String.valueOf(car[l]);
                                            Log.e(TAG," "+opstr);
                                            temp=new int[4];
                                            break;
                                        }
                                    }




                                    Log.e(TAG,"value of K "+l);
                                }
                                if(l==mar.length)
                                {
                                    opstr=opstr+"*";
                                    Log.e(TAG," "+opstr);
                                    temp=new int[4];
                                }
                                if(off_interval>2000 && off_interval<2800)
                                {
                                    opstr=opstr+" ";
                                    temp=new int[4];
                                }

                                index=0;
                            }
//                            if(off_interval>1700 && off_interval<2300)
//                            {
//                                opstr=opstr+" ";
//                            }

                        }
                    }
                    else
                    {
                        if(first_flag==true)
                        {
                            start_on=System.currentTimeMillis();
                            first_flag=false;
                        }
                        //Log.e(TAG, "---Flag off" );
                        if(state_flag==true)
                        {
                            state_flag=false;
                            start_off=System.currentTimeMillis();
                            on_interval=start_off-start_on;
                            //Toast.makeText(getBaseContext(),Html.fromHtml("<font color='#ff0000' ><b> on intyerval "+ on_interval +"</b></font>"),Toast.LENGTH_SHORT).show();
                            Log.e(TAG,"on interval="+on_interval);
                            if(on_interval>50 && on_interval<=200)
                            {

                                temp[index]=1;
                                Log.e(TAG,"stored temp["+index+"]="+temp[index]);
                                index++;
                            }
                            else if(on_interval>225 && on_interval<=400)
                            {
                                temp[index]=3;
                                Log.e(TAG,"stored temp["+index+"]="+temp[index]);
                                index++;
                            }
                        }
                        // Just a comment
                        if(state_flag==false)
                        {
                            //start_off=System.currentTimeMillis();
                            on_interval=System.currentTimeMillis()-start_on;
                            if(on_interval>8000)
                            {
                                for(l=0;l<mar.length;l++)
                                {

                                    if(mar[l].length==index) {

                                        for (m = 0; m < index; m++) {
                                            Log.e(TAG,"temp["+m+"]="+temp[m]);
                                            Log.e(TAG,"mar["+m+"]="+mar[l][m]);
                                            if (mar[l][m] != temp[m]) {
                                                Log.e(TAG,"abt to break inner ");
                                                break;

                                            }
                                        }
                                        Log.e(TAG,"first for loop broken ");
                                        if(m==index)
                                        {
                                            Log.e(TAG,"abt to break outer ");
                                            opstr=opstr+String.valueOf(car[l]);
                                            Log.e(TAG," "+opstr);
                                            temp=new int[4];
                                            break;
                                        }
                                    }




                                    Log.e(TAG,"value of K "+l);
                                }
//                                if(l==mar.length)
//                                {
//                                    opstr=opstr+"*";
//                                    Log.e(TAG,"final String is "+opstr);
//                                    temp=new int[4];
//                                }
                                Log.e(TAG,"final String is "+opstr);
                                Intent i=new Intent(receiver.this,output.class);
                                i.putExtra("output",opstr);

                                finish();
                                startActivity(i);
                            }
                        }

                    }
                 mzoomwindow.release();
        }
        catch (Exception e)
        {
            Log.e(TAG, "Sub Mat: " );
        }

        return mRgba;
       // return inputFrame.rgba();

//        Mat mRgba = inputFrame.rgba();
//        Mat mRgbaT = mRgba.t();
//        Core.flip(mRgba.t(), mRgbaT, 1);
//        Imgproc.resize(mRgbaT, mRgbaT, mRgba.size());
//        return mRgbaT;
//        Mat mRgbaT=null,mRgbaF = null;
//       Mat mRgba = inputFrame.rgba();
//        // Rotate mRgba 90 degrees
//        Core.transpose(mRgba, mRgbaT);
//
//        Imgproc.resize(mRgbaT, mRgbaF, mRgbaF.size(), 0,0, 0);
//        Core.flip(mRgbaF, mRgba, 1 );
//
//        return mRgba;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("---------","--------"+event.getAction());
        return true;
    }
}
