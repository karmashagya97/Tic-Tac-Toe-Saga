package com.example.shivam.rick_morty_toe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class splash extends AppCompatActivity implements View.OnTouchListener  {
TextView tx;
Button go;
ConstraintLayout c1,c2;
Animation uptodown,downtoup;
Context context=this;


MediaPlayer mediaPlayer;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jacky);
       c1=(ConstraintLayout)findViewById(R.id.c1);
       c2=(ConstraintLayout)findViewById(R.id.c2);
go=(Button)findViewById(R.id.go);


mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.hp);
       //Components of set dialog
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
mediaPlayer.start();


       uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
       downtoup= AnimationUtils.loadAnimation(this,R.anim.downtoup);
       c1.setAnimation(uptodown);
       c2.setAnimation(downtoup);
       go.setOnTouchListener(this);

    }








    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                go.setBackgroundResource(R.drawable.b123);
                go.setTextColor(Color.parseColor("#000000"));
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                go.setBackgroundResource(R.drawable.button_black);
                go.setTextColor(Color.parseColor("#ffffff"));
            }


        return false;
        }



    public void direct(View view) {


       // startActivity(new Intent (getApplicationContext(),MainActivity.class));

        AlertDialog.Builder mBuilder=new AlertDialog.Builder(splash.this);
        View mView=getLayoutInflater().inflate(R.layout.custom_dialog,null);
        final Button single=(Button)mView.findViewById(R.id.single);
        final Button multi=(Button)mView.findViewById(R.id.multi);

multi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mediaPlayer.stop();
        startActivity(new Intent (getApplicationContext(),MainActivity.class));
    }
});

multi.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            multi.setBackgroundResource(R.drawable.b123);
            multi.setTextColor(Color.parseColor("#000000"));
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            multi.setBackgroundResource(R.drawable.button_black);
            multi.setTextColor(Color.parseColor("#ffffff"));
        }


        return false;
    }
});


single.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            single.setBackgroundResource(R.drawable.b123);
            single.setTextColor(Color.parseColor("#000000"));
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            single.setBackgroundResource(R.drawable.button_black);
            single.setTextColor(Color.parseColor("#ffffff"));
        }

        return false;
    }
});

single.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


      /* RelativeLayout holder;
       TextView custom_Text;
       Toast t;
        holder=(RelativeLayout) getLayoutInflater().inflate(R.layout.custom_toast,(RelativeLayout)findViewById(R.id.rell));
        custom_Text=(TextView) holder.findViewById(R.id.custom_text);


        t=new Toast(getApplicationContext());
        t.setGravity(Gravity.CENTER | Gravity.BOTTOM,0,0);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setView(holder);

        custom_Text.setText("Not Available");
        custom_Text.setTextSize(25);
        t.show();*/

        startActivity(new Intent (getApplicationContext(),Main2Activity.class));

    }
});

mBuilder.setView(mView);
Dialog dialog=mBuilder.create();
dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    public void exit(View view) {
        finish();
    }
}

