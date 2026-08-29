package com.clipvault.app.util

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("clipvault_prefs", Context.MODE_PRIVATE)

    var monitoringEnabled: Boolean
        get() = prefs.getBoolean(KEY_MONITORING, true)
        set(value) = prefs.edit().putBoolean(KEY_MONITORING, value).apply()

    var floatingEnabled: Boolean
        get() = prefs.getBoolean(KEY_FLOATING, false)
        set(value) = prefs.edit().putBoolean(KEY_FLOATING, value).apply()

    var autoCleanupEnabled: Boolean
        get() = prefs.getBoolean(KEY_AUTO_CLEANUP, false)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_CLEANUP, value).apply()

    var maxClipCount: Int
        get() = prefs.getInt(KEY_MAX_CLIP, 500)
        set(value) = prefs.edit().putInt(KEY_MAX_CLIP, value).apply()

    var notificationEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION, true)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATION, value).apply()

    var deduplicateEnabled: Boolean
        get() = prefs.getBoolean(KEY_DEDUP, true)
        set(value) = prefs.edit().putBoolean(KEY_DEDUP, value).apply()

    companion object {
        private const val KEY_MONITORING = "monitoring_enabled"
        private const val KEY_FLOATING = "floating_enabled"
        private const val KEY_AUTO_CLEANUP = "auto_cleanup"
        private const val KEY_MAX_CLIP = "max_clip_count"
        private const val KEY_NOTIFICATION = "notification_enabled"
        private const val KEY_DEDUP = "deduplicate_enabled"
    }
}