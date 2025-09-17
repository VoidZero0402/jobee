package com.bluerose.jobee.data.models

data class Job(
    val id: String,
    val title: String,
    val company: Company,
    val location: String,
    val salaryRange: Pair<Int, Int>,
    val tags: List<String>
)
