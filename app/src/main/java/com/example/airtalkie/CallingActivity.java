package com.example.airtalkie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import at.markushi.ui.CircleButton;

public class CallingActivity extends AppCompatActivity implements View.OnTouchListener {

    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    // Define UI elements
    private static CircleButton audio;

    private MainConversation audioClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        audio = findViewById(R.id.audioBtn);
        audioClient = new MainConversation();

        audioClient.audioCreate();
        audioClient.setSocket(ListenThread.getSocket());
        audioClient.setupStreams();
        audioClient.startPlaying();

        // Microphone button pressed/released
        audio.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN ) {

            audioClient.stopPlaying();
            audioClient.startRecording();

        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL ) {

            audioClient.stopRecording();
            audioClient.startPlaying();

        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                // Permission granted
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioClient.destroyProcesses();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
