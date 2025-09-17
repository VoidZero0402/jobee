package com.bluerose.jobee.data.models

data class Chat(
    val company: Company,
    val lastMessage: String,
    val lastMessageTimestamp: Long,
    val unreadMessageCount: Int,
)
