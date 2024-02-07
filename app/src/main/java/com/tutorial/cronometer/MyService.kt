package com.tutorial.cronometer

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.util.concurrent.Executors

class MyService : Service() {
    private var context: Context? = null
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "100"
    private var isDestroyed = false


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        context = this
        startForeground(NOTIFICATION_ID, showNotification("This is the content"))

    }

    private fun showNotification(content: String): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID, "foreground notification",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        return NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("FS")
            .setContentText(content).setOnlyAlertOnce(true).setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(context, "Start Cronometer ...", Toast.LENGTH_SHORT).show()
        doTask()
        return super.onStartCommand(intent, flags, startId)

    }

    private fun doTask() {
        val data =IntArray(1)
        val executorService = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executorService.execute{
            for(i in 0..60){
                if (isDestroyed)
                    break
                data[0] = i
                try {
                    handler.post{
                        updateNotification(data[0].toString())
                    }
                    Thread.sleep( 500)
                }
                catch (e: InterruptedException){
                    e.printStackTrace()
                } //completar
            }

        }

    }
    private fun updateNotification(data: String){
        val notification: Notification = showNotification(data)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID,notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        isDestroyed = true
        Toast.makeText(this,"Stopping Service", Toast.LENGTH_SHORT).show()
    }


}
