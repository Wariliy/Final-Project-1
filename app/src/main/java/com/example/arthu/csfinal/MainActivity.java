package com.example.arthu.csfinal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView countdownText;
    private Button countdownButton;


    private CountDownTimer countdownTimer;
    private long timeLeftInMilliseconds = 600000; //10 mins
    private boolean timeRunning;

    NotificationCompat.Builder notification;
    private static final int uniqueID = 83481;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        }); // ButtonClick to start or stop the timer

        TextView slogan = findViewById(R.id.slogan);
        slogan.setText("Stay safe and take care!");
        TextView phoneNumberTitle = findViewById(R.id.pntitle);
        phoneNumberTitle.setText("Phone#:");

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
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
                if (timeLeftInMilliseconds == 1) {
                    notification.setSmallIcon(R.mipmap.ic_launcher);
                    notification.setTicker("ALERT");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("Alert!");
                    notification.setContentText("If you feel safe, please return to stop the timer.");

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(uniqueID, notification.build());
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


}
