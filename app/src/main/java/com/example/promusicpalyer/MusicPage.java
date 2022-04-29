package com.example.promusicpalyer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MusicPage extends AppCompatActivity {

    Button playbtn, nextbtn, prevbtn, fforwardbtn, frewindbtn;
    TextView txtsongname, timestart, timeend;
    SeekBar seekMusicbar;
    ImageView imageview;
    String songName;
    public static final String Extra_name = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> MySongs;



    Thread updateSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);
        prevbtn = findViewById(R.id.previousbtn);
        nextbtn = findViewById(R.id.nextbtn);
        playbtn = findViewById(R.id.playsongbtn);
        txtsongname = findViewById(R.id.songname);
        timestart = findViewById(R.id.lefttime);
        timeend = findViewById(R.id.righttime);
        seekMusicbar = findViewById(R.id.seekbar);
        imageview = findViewById(R.id.msicon);
        if(mediaPlayer!=null){
            mediaPlayer.start();
            mediaPlayer.release();
        }
        Intent myintent = getIntent();
        Bundle bundle = myintent.getExtras();
        MySongs = (ArrayList)bundle.getParcelableArrayList("Songs");
        String sname = myintent.getStringExtra("Songname");
        position = bundle.getInt("position",0);
        txtsongname.setSelected(true);
        Uri uri = Uri.parse(MySongs.get(position).toString());
        songName = MySongs.get(position).getName();
        txtsongname.setText(songName);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusicbar.setProgress(currentPosition);
                    }
                    catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        seekMusicbar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekMusicbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.purple_700), PorterDuff.Mode.MULTIPLY);
        seekMusicbar.getThumb().setColorFilter(getResources().getColor(R.color.purple_700),PorterDuff.Mode.SRC_IN);

        seekMusicbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {



                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = creatTime(mediaPlayer.getDuration());
        timeend.setText(endTime);
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = creatTime(mediaPlayer.getCurrentPosition());
                timestart.setText(currentTime);
                handler.postDelayed(this,delay);
            }
        },delay);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    playbtn.setBackgroundResource(R.drawable.play_arrow);
                    mediaPlayer.pause();
                }
                else
                {
                    playbtn.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                nextbtn.performClick();
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position+1)%MySongs.size();
                Uri uri = Uri.parse(MySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                songName = MySongs.get(position).getName();
                txtsongname.setText(songName);
                mediaPlayer.start();
                startAnimation(imageview,360f);

                String endTime = creatTime(mediaPlayer.getDuration());
                timeend.setText(endTime);
                seekMusicbar.setMax(mediaPlayer.getDuration());
            }
        });
        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position-1)<0?(MySongs.size()-1):position-1;
                Uri uri = Uri.parse(MySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                songName = MySongs.get(position).getName();
                txtsongname.setText(songName);
                mediaPlayer.start();
                startAnimation(imageview,-360f);

                String endTime = creatTime(mediaPlayer.getDuration());
                timeend.setText(endTime);
                seekMusicbar.setMax(mediaPlayer.getDuration());
            }
        });
    }
    public void startAnimation(View view, Float degree)
    {
        ObjectAnimator objanimatr = ObjectAnimator.ofFloat(imageview,"rotation",0f,degree);
        objanimatr.setDuration(1000);
        AnimatorSet animset = new AnimatorSet();
        animset.playTogether(objanimatr);
        animset.start();
    }

    public String creatTime(int duration){
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time = time+min+":";

        if(sec<10){
            time += "0";
        }
        time+=sec;
        return time;

    }
}