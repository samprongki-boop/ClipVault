package com.clipvault.app.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.clipvault.app.MainActivity
import com.clipvault.app.R

object NotificationHelper {

    private const val CHANNEL_CLIPBOARD = "channel_clipboard"
    private const val CHANNEL_FLOATING = "channel_floating"

    fun createChannels(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelClipboard = NotificationChannel(
            CHANNEL_CLIPBOARD,
            "Clipboard Monitor",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Monitoring clipboard in background"
            setShowBadge(false)
        }

        val channelFloating = NotificationChannel(
            CHANNEL_FLOATING,
            "Floating Panel",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Floating clipboard overlay"
            setShowBadge(false)
        }

        manager.createNotificationChannel(channelClipboard)
        manager.createNotificationChannel(channelFloating)
    }

    fun buildClipboardNotification(context: Context): android.app.Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(context, CHANNEL_CLIPBOARD