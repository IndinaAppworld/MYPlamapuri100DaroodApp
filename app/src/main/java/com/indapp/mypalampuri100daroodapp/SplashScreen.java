package com.indapp.mypalampuri100daroodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.animation.ActivityAnimator;


//import android.support.v7.app.AppCompatActivity;


public class SplashScreen extends Activity
{
    boolean isChangedStat=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView titleBar = (TextView) getWindow().findViewById(android.R.id.title);
        if (titleBar != null) {
            // set text color, YELLOW as sample
            titleBar.setTextColor(getResources().getColor(R.color.primary));
            // find parent view
            ViewParent parent = titleBar.getParent();
            if (parent != null && (parent instanceof View)) {
                // set background on parent, BRICK as sample
                View parentView = (View) parent;
                parentView.setBackgroundColor(getResources().getColor(R.color.primary));
            }
        }

        setContentView(R.layout.activity_splash);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                //start your activity here
                if (isChangedStat == false)
                {
                    isChangedStat = true;
                    startMenuScreen();
                }
            }
        }, 2000L);

    }
//    public void waduDialog()
//    {
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.dialog_wadu_confirmation);
//        dialog.setCancelable(false);
//        dialog.getWindow().getAttributes().windowAnimations = R.anim.pull_in_left;
//
//        dialog.show();
//
//        ((AlviNastaLiqBold)dialog.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//
//
//
//                startMenuScreen();
//            }
//        });
//        ((AlviNastaLiqBold)dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//
//                finish();
//            }
//        });
//    }

public void startMenuScreen()
{


//    Constants.sp=getSharedPreferences(Constants.prefName, Activity.MODE_PRIVATE);
//    Constants.editor= Constants.sp.edit();
    Intent intent=new Intent(Intent.ACTION_VIEW);
//    if(Constants.sp.getString("guzarish","false").equalsIgnoreCase("false"))
//    {
//        intent.setClass(SplashScreen.this, GizarishActivity.class);
//    }
//    else
    {
        intent.setClass(SplashScreen.this, MainActivity.class);
    }
    startActivityForResult(intent, 300);
    ActivityAnimator anim = new ActivityAnimator();

    try {
    anim.getClass().getMethod("appearTopLeft" + "Animation", Activity.class).invoke(anim, SplashScreen.this);
}
catch(Exception e ) {


}
    finish();
}


}
