package com.clipvault.app

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.clipvault.app.data.ClipDatabase
import com.clipvault.app.data.ClipRepository
import com.clipvault.app.util.Preferences

class ClipVaultApp : Application(), Configuration.Provider {

    val database by lazy { ClipDatabase.getInstance(this) }
    val repository by lazy { ClipRepository(database.clipDao()) }
    val preferences by lazy { Preferences(this) }

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, workManagerConfiguration)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}