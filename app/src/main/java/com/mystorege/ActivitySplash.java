package com.mystorege;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySplash extends Activity {
    private Handler handler;
    private Handler handler1;
    private TextView textView;
    private TextView textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(!isConnected(ActivitySplash.this)){
            buildDialog(ActivitySplash.this).show();
        }

        else {
            Toast.makeText(ActivitySplash.this,"Welcome", Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.activity_main);
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ActivitySplash.this,RecyclerActivity.class));
                }
            },3000L);
        }

        textView = (TextView) findViewById(R.id.tv);
        textView1 = (TextView) findViewById(R.id.tv2);
        handler = new Handler();
        handler1 = new Handler();

        fade(textView);
        fade(textView1);
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please Check Your Internet Connection and Try Again");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

    private Runnable updateTextRunnable = new Runnable() {
        public void run() {
            String message = "My Storage";
            textView.setText(message);
            handler.postDelayed(this, 1000);
        }
    };

    private Runnable updateTextRunnable1 = new Runnable() {
        public void run() {
            String message = "Store whatever you need";
            textView1.setText(message);
            handler1.postDelayed(this, 1000);
        }
    };

    public void onResume() {
        super.onResume();
        handler.postDelayed(updateTextRunnable, 1000);
        handler1.postDelayed(updateTextRunnable1, 1000);
    }

    public void fade(View view){
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade);
        textView.startAnimation(animation1);
        textView1.startAnimation(animation1);
    }


}


