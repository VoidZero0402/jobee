package com.bluerose.jobee.di

object AppSharedPrefs {
    const val PREFS_NAME = "app_prefs"

    object Keys {
        const val IS_AUTHORIZED = "is_authorized"
        const val USERNAME = "username"
        const val EMAIL = "email"
        const val SAVED_JOBS = "saved_jobs"
        const val RECOMMENDED_JOBS = "recommended_jobs"
        const val RECOMMENDED_JOBS_UPDATE_TIMESTAMP = "recommended_jobs_update_timestamp"
    }
}