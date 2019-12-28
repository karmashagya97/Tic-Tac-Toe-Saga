package com.example.shivam.rick_morty_toe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
//Single Player
public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    Button [][] buttons=new Button[3][3];
     Dialog dialog;
    private boolean player1Turn=true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView p1;
    private TextView p2;
    ConstraintLayout cc1,cc2;
    Animation uptodown,downtoup;

    RelativeLayout holder;
    TextView custom_Text;
    Toast t;
    MediaPlayer mediaPlayer;
Button cancel;
    String human,com;
int c[][];

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        p1=(TextView)findViewById(R.id.p1);
        p2=(TextView)findViewById(R.id.p2);
        cc1=(ConstraintLayout)findViewById(R.id.cc1);
        cc2=(ConstraintLayout)findViewById(R.id.cc2);

        uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup= AnimationUtils.loadAnimation(this,R.anim.downtoup);
        cc1.setAnimation(uptodown);
        cc2.setAnimation(downtoup);


        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.voices);



        mediaPlayer.start();


        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        holder=(RelativeLayout) getLayoutInflater().inflate(R.layout.custom_toast,(RelativeLayout)findViewById(R.id.rell));
        custom_Text=(TextView) holder.findViewById(R.id.custom_text);


        t=new Toast(getApplicationContext());
        t.setGravity(Gravity.CENTER | Gravity.BOTTOM,0,0);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setView(holder);


         c=new int[4][4];



        custom_Text.setText("Let's Begin The Battle");
        custom_Text.setTextSize(25);
        t.show();

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonID ="button_"+i+j;
                int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j]=(Button)findViewById(resID);

                buttons[i][j].setOnClickListener(this);

              c[i][j]=2;
            }
        }


        AlertDialog.Builder mBuilder=new AlertDialog.Builder(Main2Activity.this);
        View mView=getLayoutInflater().inflate(R.layout.custom_dialog1,null);
        final Button x=(Button)mView.findViewById(R.id.x);
        final Button o=(Button)mView.findViewById(R.id.o);
        final Button cancel=(Button)mView.findViewById(R.id.cancel);
        final Dialog dialog;
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            human="0";
            com="X";
                custom_Text.setText("You Choose 0");
                custom_Text.setTextSize(25);
                t.show();
            }
        });

        o.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    o.setBackgroundResource(R.drawable.circl_white);
                    o.setTextColor(Color.parseColor("#000000"));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    o.setBackgroundResource(R.drawable.circle_black);
                    o.setTextColor(Color.parseColor("#ffffff"));
                }


                return false;
            }
        });


        x.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    x.setBackgroundResource(R.drawable.circl_white);
                    x.setTextColor(Color.parseColor("#000000"));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    x.setBackgroundResource(R.drawable.circle_black);
                    x.setTextColor(Color.parseColor("#ffffff"));
                }

                return false;
            }
        });

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              human="X";
              com="0";
                custom_Text.setText("You Choosed X");
                custom_Text.setTextSize(25);
                t.show();

            }
        });

        mBuilder.setView(mView);
        dialog=mBuilder.create();
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });



        Button reset=(Button)findViewById(R.id.reset);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        mediaPlayer.release();
    }

    @Override
    public void onClick(View view) {

        if(!((Button )view).getText().toString().equals("")){
            return;
        }

        if(player1Turn) {

            ((Button) view).setText(human);
            ((Button) view).setEnabled(false);
            String a = getResources().getResourceEntryName(view.getId());
            String p = a.substring(7, 8);
            String q = a.substring(8);

            int x = Integer.parseInt(p);
            int y = Integer.parseInt(q);


            ((Button) view).setTextColor(Color.parseColor("#000000"));
            ((Button) view).setBackgroundColor(Color.parseColor("#ffffff"));
            c[x][y] = 0;



            player1Turn = !player1Turn;

        }
         else {
            takeTurn();
            player1Turn = !player1Turn;
        }

            // Do something after 5s = 5000ms


            roundCount++;

            if (checkForWin()) {
                if (player1Turn) {
                    player1Wins();
                } else {
                    player2Wins();
                }
            } else if (roundCount == 9) {
                draw();
            } else {

            }


    }

    private void draw() {

        custom_Text.setText("Draw");
        custom_Text.setTextSize(25);
        t.show();
playAgain();
        resetBoard();
    }

    private void player1Wins() {
        player1Points++;
        custom_Text.setText(human+" Wins");
        custom_Text.setTextSize(25);
        t.show();
        playAgain();
        updatePointsText();
        resetBoard();
    }



    @SuppressLint("SetTextI18n")
    private void player2Wins() {
        player2Points++;
        custom_Text.setText(com+" Wins");
        custom_Text.setTextSize(25);
        t.show();
        playAgain();
        updatePointsText();
        resetBoard();
    }


    private void updatePointsText() {

        p1.setText(human+":"+player1Points);
        p2.setText(com+": "+player2Points);
    }
    boolean count=true;
    private void resetBoard() {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){

                count=!count;
                c[i][j]=2;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setTextColor(Color.parseColor("#ffffff"));
                if(count==true) {
                    buttons[i][j].setBackgroundColor(Color.parseColor("#ffffff"));
                }
                else{
                    buttons[i][j].setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        }

        roundCount=0;

    }


    private boolean checkForWin(){
        String[][] field=new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){

                return true;

            }
        }


        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){

                return true;

            }
        }


        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){

            return true;

        }

        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){

            return true;

        }


        return false;

    }


    public void reset(View view) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){

                count=!count;
                c[i][j]=2;
                buttons[i][j].setText("");
                buttons[i][j].setTextColor(Color.parseColor("#000000"));
                buttons[i][j].setEnabled(true);
                if(count==true) {
                    buttons[i][j].setBackgroundColor(Color.parseColor("#ffffff"));
                }
                else{
                    buttons[i][j].setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        }

        roundCount=0;



    }





        public void takeTurn() {


            if (c[0][0] == 2 &&
                    ((c[0][1] == 0 && c[0][2] == 0) ||
                            (c[1][1] == 0 && c[2][2] == 0) ||
                            (c[1][0] == 0 && c[2][0] == 0))) {
                markSquare(0, 0);
            } else if (c[0][1] == 2 &&
                    ((c[1][1] == 0 && c[2][1] == 0) ||
                            (c[0][0] == 0 && c[0][2] == 0))) {
                markSquare(0, 1);
            } else if (c[0][2] == 2 &&
                    ((c[0][0] == 0 && c[0][1] == 0) ||
                            (c[2][0] == 0 && c[1][1] == 0) ||
                            (c[1][2] == 0 && c[2][2] == 0))) {
                markSquare(0, 2);
            } else if (c[1][0] == 2 &&
                    ((c[1][1] == 0 && c[1][2] == 0) ||
                            (c[0][0] == 0 && c[2][0] == 0))) {
                markSquare(1, 0);
            } else if (c[1][1] == 2 &&
                    ((c[0][0] == 0 && c[2][2] == 0) ||
                            (c[0][1] == 0 && c[2][1] == 0) ||
                            (c[2][0] == 0 && c[0][2] == 0) ||
                            (c[1][0] == 0 && c[1][2] == 0))) {
                markSquare(1, 1);
            } else if (c[1][2] == 2 &&
                    ((c[1][0] == 0 && c[1][1] == 0) ||
                            (c[0][2] == 0 && c[2][2] == 0))) {
                markSquare(1, 2);
            } else if (c[2][0] == 2 &&
                    ((c[0][0] == 0 && c[1][0] == 0) ||
                            (c[2][1] == 0 && c[2][2] == 0) ||
                            (c[1][1] == 0 && c[0][2] == 0))) {
                markSquare(2, 0);
            } else if (c[2][1] == 2 &&
                    ((c[0][1] == 0 && c[1][2] == 0) ||
                            (c[2][0] == 0 && c[2][2] == 0))) {
                markSquare(2, 1);
            } else if (c[2][2] == 2 &&
                    ((c[0][0] == 0 && c[1][1] == 0) ||
                            (c[0][2] == 0 && c[1][2] == 0) ||
                            (c[2][0] == 0 && c[2][1] == 0))) {
                markSquare(2, 2);
            } else {
                Random rand = new Random();

                int a = rand.nextInt(3);
                int b = rand.nextInt(3);
                while (a == 0 || b == 0 || c[a][b] != 2) {
                    a = rand.nextInt(3);
                    b = rand.nextInt(3);
                }
                markSquare(a, b);
            }



    }


    private void markSquare(int x, int y) {



        buttons[x][y].setEnabled(false);
        buttons[x][y].setText(com);
        buttons[x][y].setTextColor(Color.parseColor("#ffffff"));
        buttons[x][y].setBackgroundColor(Color.parseColor("#000000"));
        c[x][y] = 1;

    }


    @SuppressLint("ClickableViewAccessibility")
    public void playAgain(){


        AlertDialog.Builder mBuilder=new AlertDialog.Builder(Main2Activity.this);
        View mView=getLayoutInflater().inflate(R.layout.custom_dialog2,null);
        final Button yes=(Button)mView.findViewById(R.id.yes);
        final Button no=(Button)mView.findViewById(R.id.no);
        mBuilder.setView(mView);
        dialog=mBuilder.create();
        dialog.show();

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         // Intent i=new Intent(getApplicationContext(),splash.class);
         //  startActivity(i);
dialog.cancel();
finish();

            }
        });

        no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    no.setBackgroundResource(R.drawable.button_red);
                    no.setTextColor(Color.parseColor("#ffffff"));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    no.setBackgroundResource(R.drawable.b123);
                    no.setTextColor(Color.parseColor("#000000"));
                }


                return false;
            }
        });


        yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    yes.setBackgroundResource(R.drawable.b123);
                    yes.setTextColor(Color.parseColor("#000000"));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    yes.setBackgroundResource(R.drawable.button_green);
                    yes.setTextColor(Color.parseColor("#ffffff"));
                }

                return false;
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i=new Intent(getApplicationContext(),Main2Activity.class);
              startActivity(i);

            }
        });


    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount=savedInstanceState.getInt("roundCCount");
        player1Points=savedInstanceState.getInt("player1Points");
        player2Points=savedInstanceState.getInt("player2Points");
        player1Turn=savedInstanceState.getBoolean("player1Turn");

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


