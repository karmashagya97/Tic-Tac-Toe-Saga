package com.example.shivam.rick_morty_toe;

import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
//Two Player
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button [][] buttons=new Button[3][3];

    boolean player1Turn=true;

    private int roundCount;

    private int player1Points;
    private int player2Points;
       TextView rt;
    private TextView p1;
    private TextView p2;
ConstraintLayout cc1,cc2;
    Animation uptodown,downtoup;
    ImageView imageView6,imageView7;

    RelativeLayout holder;
    TextView custom_Text;
    Toast t;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_layout);

        p1=(TextView)findViewById(R.id.p1);
        p2=(TextView)findViewById(R.id.p2);
        cc1=(ConstraintLayout)findViewById(R.id.cc1);
        cc2=(ConstraintLayout)findViewById(R.id.cc2);


        uptodown= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup= AnimationUtils.loadAnimation(this,R.anim.downtoup);
        cc1.setAnimation(uptodown);
        cc2.setAnimation(downtoup);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.voices);





        mediaPlayer.start();



        holder=(RelativeLayout) getLayoutInflater().inflate(R.layout.custom_toast,(RelativeLayout)findViewById(R.id.rell));
        custom_Text=(TextView) holder.findViewById(R.id.custom_text);


        t=new Toast(getApplicationContext());
        t.setGravity(Gravity.CENTER | Gravity.BOTTOM,0,0);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setView(holder);

        custom_Text.setText("Let's Begin The Battle");
        custom_Text.setTextSize(25);
        t.show();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonID ="button_"+i+j;
                int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j]=(Button)findViewById(resID);

                buttons[i][j].setOnClickListener(this);


            }
        }






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

        if(player1Turn){

            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#000000"));
            ((Button)view).setBackgroundColor(Color.parseColor("#ffffff"));


        }
        else{

            ((Button)view).setText("0");
            ((Button)view).setTextColor(Color.parseColor("#ffffff"));
            ((Button)view).setBackgroundColor(Color.parseColor("#000000"));

        }

        roundCount++;

        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }
            else{
                player2Wins();
            }
        }
        else if(roundCount==9){
            draw();
        }
        else{
            player1Turn=!player1Turn;
        }


    }

    private void draw() {

        custom_Text.setText("Draw");
        custom_Text.setTextSize(25);
        t.show();

        resetBoard();
    }

    private void player1Wins() {
        player1Points++;
        custom_Text.setText("X Wins");
        custom_Text.setTextSize(25);
        t.show();
        updatePointsText();
        resetBoard();
    }



    private void player2Wins() {
        player2Points++;
        custom_Text.setText("0 Wins");
        custom_Text.setTextSize(25);
        t.show();
        updatePointsText();
        resetBoard();
    }


    private void updatePointsText() {

        p1.setText("X : "+player1Points);
        p2.setText("0 : "+player2Points);
    }
boolean count=true;
    private void resetBoard() {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){

                count=!count;
                buttons[i][j].setText("");
                buttons[i][j].setTextColor(Color.parseColor("#383838"));
                if(count==true) {
                    buttons[i][j].setBackgroundColor(Color.parseColor("#383838"));
                }
                else{
                    buttons[i][j].setBackgroundColor(Color.parseColor("#383838"));
                }
            }
        }
        roundCount=0;
        player1Turn=!player1Turn;

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
       player1Points=0;
       player2Points=0;
       updatePointsText();
       resetBoard();

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
}
