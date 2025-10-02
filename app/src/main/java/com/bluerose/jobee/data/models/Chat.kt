package com.bluerose.jobee.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    val company: Company,
    val lastMessage: String,
    val lastMessageTimestamp: Long,
    val unreadMessageCount: Int,
) : Parcelable