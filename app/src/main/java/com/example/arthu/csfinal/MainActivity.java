package com.example.arthu.csfinal;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.arthu.csfinal.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {
    private TextView countdownText;
    private Button countdownButton;
    private Button resumeButton;


    private CountDownTimer countdownTimer;
    private long timeLeftInMilliseconds = 600000; //10 mins
    private boolean timeRunning;

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);
        resumeButton = findViewById(R.id.countdown_resume);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();

            }
        }); // ButtonClick to start or stop the timer

        updateTimer();

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeTimer();
            }
        });

        TextView slogan = findViewById(R.id.slogan);
        slogan.setText("Stay safe and take care!");
        TextView phoneNumberTitle = findViewById(R.id.pntitle);
        phoneNumberTitle.setText("Phone#:");


    }

    /**
     * if time is running, stop the timer
     * if not, start the timer
     */
    public void startStop() {
        if (timeRunning) {
            stopTimer();
        } else {
            startTimer();

        }
    }



    public void startTimer() {
        countdownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
                if (timeLeftInMilliseconds < 60000) {
                    sendNotification();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

        countdownButton.setText("PAUSE");
        timeRunning = true;
    }

    public void stopTimer() {
        countdownTimer.cancel();
        countdownButton.setText("Start");
        timeRunning = false;
    }

    public void resumeTimer() {
        countdownTimer.cancel();
        timeRunning = false;
        String timeLeftText;

        timeLeftText = " 10:00";
        timeLeftInMilliseconds = 600000;
        countdownText.setText(timeLeftText);
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = " " + minutes;
        timeLeftText += ":";
        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;

        countdownText.setText(timeLeftText);

    }

    public void sendNotification() {
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_icon)
                .setContentTitle("ALERT!!")
                .setContentText("If you feel safe, please return to app to stop the timer.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(Color.RED)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);



    }
}
