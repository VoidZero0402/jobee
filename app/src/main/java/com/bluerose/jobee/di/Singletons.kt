package com.bluerose.jobee.di

import android.content.Context
import android.content.SharedPreferences
import com.bluerose.jobee.data.SampleRepository

object Singletons {
    lateinit var sharedPrefs: SharedPreferences
        private set

    lateinit var repository: SampleRepository
        private set

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(AppSharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
        repository = SampleRepository(sharedPrefs)
    }
}