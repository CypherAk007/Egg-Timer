package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controlerButton;
    Boolean counterIsActive=false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controlerButton.setText("GO");
        timerSeekBar.setEnabled(true);
        counterIsActive=false;
    }

    public void updateTimer(int secondsLeft){
        int minitues = (int) (secondsLeft/60);
        int seconds= secondsLeft-  minitues*60;

        timerTextView.setText(Integer.toString(minitues)+":"+Integer.toString(seconds));

        String secondString = Integer.toString(seconds);

        if (seconds<=9){
            secondString="0"+secondString;
        }
        timerTextView.setText(Integer.toString(minitues)+":"+secondString);
    }

    public void controlTimer(View view){

        if (counterIsActive== false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            Toast.makeText(MainActivity.this, "button clicked", Toast.LENGTH_SHORT).show();
            controlerButton.setText("Stop");

            countDownTimer=new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    timerTextView.setText("0:00");
                    Toast.makeText(MainActivity.this, "Timer is done", Toast.LENGTH_SHORT).show();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();

                }
            }.start();
        } else{
            resetTimer();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar= (SeekBar) findViewById(R.id.TimerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controlerButton = (Button)findViewById(R.id.controlerButton);

        timerSeekBar.setMax(600);//10 min timer
        timerSeekBar.setProgress(30);// initial display

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}