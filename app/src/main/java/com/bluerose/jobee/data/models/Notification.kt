package com.bluerose.jobee.data.models

data class Notification(
    val title: String,
    val description: String,
    val timestamp: Long,
    val isUnread: Boolean
)
