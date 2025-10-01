package com.bluerose.jobee.data

import android.content.SharedPreferences
import com.bluerose.jobee.data.models.Application
import com.bluerose.jobee.data.models.Chat
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.data.models.Notification

inline fun <T> List<T>.applyIf(condition: Boolean, block: (List<T>) -> List<T>): List<T> {
    return if (condition) block(this) else this
}

class SampleRepository(private val sharedPrefs: SharedPreferences) {

    enum class JobSortType {
        DEFAULT,
        TITLE,
        DATE,
        SALARY,
        LOCATION
    }

    fun getJobs(search: String = "", category: String = "", sortType: JobSortType = JobSortType.DEFAULT): List<Job> {
        return SampleDataSource.jobs
            .applyIf(search.isNotEmpty()) {
                it.filter { job -> job.title.contains(search) }
            }
            .applyIf(SampleDataSource.categories.contains(category)) {
                it.filter { job -> job.company.category.equals(category, ignoreCase = true) }
            }
            .applyIf(sortType != JobSortType.DEFAULT) {
                when (sortType) {
                    JobSortType.TITLE -> it.sortedByDescending { job -> job.title }
                    JobSortType.DATE -> it.sortedByDescending { job -> job.createdAt }
                    JobSortType.SALARY -> it.sortedByDescending { job -> job.salaryRange.second }
                    JobSortType.LOCATION -> it.sortedByDescending { job -> job.location }
                    else -> it
                }
            }
    }

    fun getSavedJobs(): List<Job> {
        return SampleDataSource.jobs
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