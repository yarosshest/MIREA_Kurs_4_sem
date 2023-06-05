package com.example.mirea_kurs_4_sem.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mirea_kurs_4_sem.R


class TimerService : Service() {
    private lateinit var builder : NotificationCompat.Builder
    private lateinit var notificationManager : NotificationManagerCompat

    private lateinit var timer : CountDownTimer

    companion object {
        const val COUNTDOWN_BR = "com.example.mirea_kurs_4_sem.timer"
        val intent = Intent(COUNTDOWN_BR)
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "1"
        private val TAG = "TimerService"
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intentNotification = Intent(applicationContext, TimerFragment::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intentNotification, PendingIntent.FLAG_IMMUTABLE)


        builder = applicationContext?.let {
            NotificationCompat.Builder(it, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_timer)
                .setContentTitle("Напоминание")
                .setContentText("Время просмотра законченно")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
        }!!
        notificationManager = applicationContext?.let { NotificationManagerCompat.from(it) }!!
        createNotificationChannel()
        val millis : Long = intent!!.getLongExtra("time", 3000)


        timer = object : CountDownTimer(millis, 100){
            override fun onTick(millisUntilFinished: Long) {
                Companion.intent.putExtra("countdown",millisUntilFinished);
                sendBroadcast(Companion.intent);
            }

            override fun onFinish() {
                createNotificationChannel()
                Companion.intent.putExtra("countdown",0)
                sendBroadcast(Companion.intent);
                with(applicationContext.let { NotificationManagerCompat.from(it) }) {
                    // notificationId is a unique int for each notification that you must define
                    if (applicationContext.let {
                            androidx.core.app.ActivityCompat.checkSelfPermission(
                                it,
                                android.Manifest.permission.POST_NOTIFICATIONS
                            )
                        } != android.content.pm.PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    this.notify(
                        TimerFragment.NOTIFICATION_ID,
                        builder.build()
                    )
                }
            }
        }
        timer.start()
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }



    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "Напоминание"
        val descriptionText = "Время просмотра законченно"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(TimerFragment.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }



    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}