package com.example.notificationpocs

import android.annotation.TargetApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_main.*
import android.app.PendingIntent
import android.content.Intent
import android.app.NotificationManager
import android.content.Context
import android.app.NotificationChannel
import android.graphics.Color

import android.os.Build
import android.support.v4.app.NotificationCompat
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private val NOTIFICATION_CHANNEL_NAME = "mychannel"
    private var notificationCompatBuilder: NotificationCompat.Builder? = null
    private var smallLayout: RemoteViews? = null
    private var largeLayout: RemoteViews? = null
    private var largeLayoutCustomAction: RemoteViews? = null
    private var notificationManager: NotificationManager? = null

    private var contentIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
         contentIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)
        button.setOnClickListener { generateNotification(true) }
        button2.setOnClickListener { generateNotification(false) }

    }

    private fun generateNotification(isDefault: Boolean) {
        inflateLayout()
        notificationCompatBuilder = NotificationCompat.Builder(
            applicationContext, NOTIFICATION_CHANNEL_ID
        )
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            createNotificationChannel()
        if (isDefault)
            notificationWithDefaultAction()
        else
            notificationWithCustomAction()

        val notification = notificationCompatBuilder!!.build()

        Picasso.get().load("https://goo.gl/static/Firebase.png").into(largeLayout!!, R.id.img3, 1, notification)
        Picasso.get().load("https://goo.gl/static/Firebase.png").into(largeLayout!!, R.id.img2, 1, notification)
        Picasso.get().load("https://goo.gl/static/Firebase.png").into(largeLayout!!, R.id.img1, 1, notification)

        Picasso.get().load("https://goo.gl/static/Firebase.png").into(largeLayoutCustomAction!!, R.id.img3, 1, notification)
        Picasso.get().load("https://goo.gl/static/Firebase.png").into(largeLayoutCustomAction!!, R.id.img2, 1, notification)
        Picasso.get().load("https://goo.gl/static/Firebase.png").into(largeLayoutCustomAction!!, R.id.img1, 1, notification)

        largeLayoutCustomAction!!.setOnClickPendingIntent(R.id.action_btn,contentIntent)
        notificationManager!!.notify(1, notification)
    }

    private fun inflateLayout() {
        smallLayout = RemoteViews(packageName, R.layout.notification_layout)
        largeLayout = RemoteViews(packageName, R.layout.notification_layout_large)
        largeLayoutCustomAction = RemoteViews(packageName, R.layout.notification_layout_large_action)
    }

    companion object {
        val NOTIFICATION_CHANNEL_ID = "4565"
    }

    fun notificationWithCustomAction() {
       notificationCompatBuilder!!
            .setSmallIcon(R.drawable.sample)
            .setCustomBigContentView(largeLayoutCustomAction)
            .setCustomContentView(smallLayout)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setWhen(System.currentTimeMillis())
    }

    fun notificationWithDefaultAction() {
        notificationCompatBuilder!!
            .setSmallIcon(R.drawable.sample)
            .setCustomBigContentView(largeLayout)
            .setCustomContentView(smallLayout)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .addAction(R.drawable.notification_icon_background, "View", contentIntent)
            .setColor(Color.MAGENTA)
            .setWhen(System.currentTimeMillis())

    }

    @TargetApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {

        val channelName = NOTIFICATION_CHANNEL_NAME
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        notificationManager!!.createNotificationChannel(notificationChannel)

    }
}
