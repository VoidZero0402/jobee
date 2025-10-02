package com.bluerose.jobee.ui.theme

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.bluerose.jobee.di.SettingsSharedPrefs

class ThemeManager(private val sharedPrefs: SharedPreferences) {

    enum class ThemeMode(val value: Int) {
        LIGHT_MODE(AppCompatDelegate.MODE_NIGHT_NO),
        NIGHT_MODE(AppCompatDelegate.MODE_NIGHT_YES),
        SYSTEM_MODE(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        companion object {
            fun fromValue(value: Int) = entries.firstOrNull { it.value == value } ?: SYSTEM_MODE
        }
    }

    fun applyTheme() {
        val theme = getSavedTheme()
        if (AppCompatDelegate.getDefaultNightMode() != theme.value) {
            AppCompatDelegate.setDefaultNightMode(theme.value)
        }
    }

    fun setTheme(theme: ThemeMode) {
        sharedPrefs.edit {
            putInt(SettingsSharedPrefs.Keys.THEME, theme.value)
        }
        applyTheme()
    }

    private fun getSavedTheme(): ThemeMode {
        return ThemeMode.fromValue(sharedPrefs.getInt(SettingsSharedPrefs.Keys.THEME, ThemeMode.SYSTEM_MODE.value))
    }
}