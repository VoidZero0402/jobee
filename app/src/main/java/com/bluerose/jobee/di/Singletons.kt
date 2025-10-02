package com.bluerose.jobee.di

import android.content.Context
import android.content.SharedPreferences
import com.bluerose.jobee.data.SampleRepository
import com.bluerose.jobee.ui.theme.ThemeManager

object Singletons {
    lateinit var sharedPrefs: SharedPreferences
        private set

    lateinit var settingsSharedPrefs: SharedPreferences
        private set

    lateinit var repository: SampleRepository
        private set

    lateinit var themeManager: ThemeManager
        private set

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(AppSharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
        settingsSharedPrefs = context.getSharedPreferences(SettingsSharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
        repository = SampleRepository(sharedPrefs)
        themeManager = ThemeManager(settingsSharedPrefs)
    }
}