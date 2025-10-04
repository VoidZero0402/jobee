package com.bluerose.jobee.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.bluerose.jobee.data.models.Application
import com.bluerose.jobee.data.models.Chat
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.data.models.Notification
import com.bluerose.jobee.data.models.User
import com.bluerose.jobee.di.AppSharedPrefs

inline fun <T> List<T>.applyIf(condition: Boolean, block: (List<T>) -> List<T>): List<T> {
    return if (condition) block(this) else this
}

class SampleRepository(private val sharedPrefs: SharedPreferences) {

    fun signInUser(user: User) {
        sharedPrefs.edit {
            putString(AppSharedPrefs.Keys.USERNAME, user.username)
            putString(AppSharedPrefs.Keys.EMAIL, user.email)
            putBoolean(AppSharedPrefs.Keys.IS_AUTHORIZED, true)
        }
    }

    fun signOutUser() {
        sharedPrefs.edit {
            putBoolean(AppSharedPrefs.Keys.IS_AUTHORIZED, false)
        }
    }

    fun isUserAuthorized(): Boolean {
        return sharedPrefs.getBoolean(AppSharedPrefs.Keys.IS_AUTHORIZED, false)
    }

    fun getUser(): User {
        return User(
            sharedPrefs.getString(AppSharedPrefs.Keys.USERNAME, "") ?: "",
            sharedPrefs.getString(AppSharedPrefs.Keys.EMAIL, "") ?: ""
        )
    }

    private fun getSavedJobIds(): Set<String> {
        return sharedPrefs.getStringSet(AppSharedPrefs.Keys.SAVED_JOBS, emptySet()) ?: emptySet()
    }

    private fun getRecommendedJobIds(): Set<String> {
        return sharedPrefs.getStringSet(AppSharedPrefs.Keys.RECOMMENDED_JOBS, emptySet()) ?: emptySet()
    }

    private fun getRecommendedJobsUpdateTimestamp(): Long {
        return sharedPrefs.getLong(AppSharedPrefs.Keys.RECOMMENDED_JOBS_UPDATE_TIMESTAMP, 0L)
    }

    enum class JobSortType {
        DEFAULT,
        TITLE,
        DATE,
        SALARY,
        LOCATION
    }

    fun getJobs(search: String = "", category: String = "", sortType: JobSortType = JobSortType.DEFAULT): List<Job> {
        val savedJobIds = getSavedJobIds()
        return SampleDataSource.jobs
            .applyIf(search.isNotEmpty()) {
                it.filter { job -> job.title.contains(search, ignoreCase = true) }
            }
            .applyIf(SampleDataSource.categories.contains(category)) {
                it.filter { job -> job.company.category.equals(category, ignoreCase = true) }
            }
            .applyIf(sortType != JobSortType.DEFAULT) {
                when (sortType) {
                    JobSortType.TITLE -> it.sortedBy { job -> job.title }
                    JobSortType.DATE -> it.sortedByDescending { job -> job.createdAt }
                    JobSortType.SALARY -> it.sortedByDescending { job -> job.salaryRange.second }
                    JobSortType.LOCATION -> it.sortedBy { job -> job.location }
                    else -> it
                }
            }
            .map { if (savedJobIds.contains(it.id)) it.copy(isSaved = true) else it }
    }

    fun getRecommendedJobs(count: Int = 8): List<Job> {
        val savedJobIds = getSavedJobIds()
        var recommendedJobIds = getRecommendedJobIds()
        val recommendedJobsUpdateTimestamp = getRecommendedJobsUpdateTimestamp()
        val now = System.currentTimeMillis()
        val twelveHours = 12 * 60 * 60 * 1000

        if (now - recommendedJobsUpdateTimestamp > twelveHours || recommendedJobIds.isEmpty()) {
            val jobs = SampleDataSource.jobs.shuffled().take(count)
            recommendedJobIds = jobs.map { it.id }.toSet()
            sharedPrefs.edit {
                putStringSet(AppSharedPrefs.Keys.RECOMMENDED_JOBS, recommendedJobIds)
                putLong(AppSharedPrefs.Keys.RECOMMENDED_JOBS_UPDATE_TIMESTAMP, now)
            }
        }

        return SampleDataSource.jobs
            .filter { recommendedJobIds.contains(it.id) }
            .map { if (savedJobIds.contains(it.id)) it.copy(isSaved = true) else it }
    }

    fun getSavedJobs(): List<Job> {
        val savedJobIds = getSavedJobIds()
        return SampleDataSource.jobs.filter { savedJobIds.contains(it.id) }.map { it.copy(isSaved = true) }
    }

    fun saveJob(id: String) {
        val savedJobIds = getSavedJobIds().toMutableSet()
        savedJobIds.add(id)
        sharedPrefs.edit {
            putStringSet(AppSharedPrefs.Keys.SAVED_JOBS, savedJobIds)
        }
    }

    fun unsaveJob(id: String) {
        val savedJobIds = getSavedJobIds().toMutableSet()
        savedJobIds.remove(id)
        sharedPrefs.edit {
            putStringSet(AppSharedPrefs.Keys.SAVED_JOBS, savedJobIds)
        }
    }

    fun getCategories(): List<String> {
        return SampleDataSource.categories
    }

    fun getApplications(): List<Application> {
        return SampleDataSource.applications
    }

    fun getChats(): List<Chat> {
        return SampleDataSource.chats
    }

    fun getNotifications(): List<Notification> {
        return SampleDataSource.notifications
    }
}