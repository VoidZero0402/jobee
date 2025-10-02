package com.bluerose.jobee.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    val id: String,
    val title: String,
    val company: Company,
    val location: String,
    val salaryRange: Pair<Int, Int>,
    val tags: List<String>,
    val createdAt: Long,
    val isSaved: Boolean = false
) : Parcelable