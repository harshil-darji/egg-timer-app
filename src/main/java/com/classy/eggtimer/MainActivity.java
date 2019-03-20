package com.classy.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button startButton;
    TextView timeText;
    MediaPlayer mplayer;
    CountDownTimer countDownTimer;

    Boolean counterIsActive = false;

    public void resetTimer()
    {
        seekBar.setProgress(30);
        timeText.setText("00:30");
        seekBar.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        startButton.setText("START");
        seekBar.setEnabled(true);

        counterIsActive = false;
    }

    public void updateTime(int progress)
    {
        int minutes = (int) progress/60;
        int secs = progress - minutes*60;

        String secondStr="";
        if(secs<=9)
        {
            secondStr = "0"+String.valueOf(secs);
        }
        else
        {
            secondStr = String.valueOf(secs);
        }
        String firstStr = String.valueOf(minutes);
        String timeStr = firstStr + ":" + secondStr;

        timeText.setText(timeStr);

    }

    public void startClick(View view)
    {
        if(!counterIsActive) {

            counterIsActive = true;
            seekBar.setEnabled(false);

            startButton.setText("STOP");

            Log.d("Button pressed", "OK");

            seekBar.setVisibility(View.GONE);

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                public void onTick(long milliSecondsUntilDone) {
                    Log.d("ontick function", " called");
                    updateTime((int) milliSecondsUntilDone / 1000);
                }

                public void onFinish() {
                    Log.i("Done!", "Counter over");
                    resetTimer();
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                }
            }.start();
        }
        else{
            resetTimer();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);
        startButton = findViewById(R.id.startButton);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar=(SeekBar)findViewById(R.id.seekBar);

        seekBar.setMax(600);    //10 mins timer

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                Log.d("Seekbar value : ",Integer.toString(progress));

                updateTime(progress);
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
