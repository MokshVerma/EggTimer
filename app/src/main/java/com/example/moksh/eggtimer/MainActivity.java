package com.example.moksh.eggtimer;

import android.media.MediaPlayer;
import android.os.ConditionVariable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView timer;
    Button stop, start;
    CountDownTimer countDown;
    MediaPlayer mediaPlayer;

    public void timerDone() {
        start.animate().alpha(1f);
        stop.animate().alpha(0f);
        start.setVisibility(View.VISIBLE);
        stop.setVisibility(View.GONE);
        countDown.cancel();
        timer.setText("00:00");
        seekBar.setProgress(0);
        seekBar.setEnabled(true);
    }

    public void start (View V){
        start = findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        stop.animate().alpha(1f);
        start.animate().alpha(0f);
        start.setVisibility(View.GONE);
        stop.setVisibility(View.VISIBLE);
        countDown = new CountDownTimer(seekBar.getProgress()*1000, 1000){
            public void onTick(long milliseconds) {
                String min1 = String.format("%02d", (milliseconds/1000)/60);
                String sec1 = String.format("%02d", (milliseconds/1000) - ((milliseconds/1000)/60)*60);
                timer.setText(min1 + ":" + sec1 );
                seekBar.setProgress((int)milliseconds/1000);
                seekBar.setEnabled(false);
            }
            public void onFinish(){
                timerDone();
                mediaPlayer.start();
            }

        }.start();



    }

    public void stop (View view){
        start = findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        timerDone();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.air_horn);
        timer = (TextView) findViewById(R.id.textView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String min = String.format("%02d", progress/60);
                String sec = String.format("%02d", progress - (progress/60)*60);
                Log.i(String.valueOf(min), String.valueOf(sec));
                timer.setText( min + ":" + sec);
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
