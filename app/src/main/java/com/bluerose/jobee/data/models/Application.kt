package com.bluerose.jobee.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Application(
    val job: Job,
    val stage: Stage
) : Parcelable {
    enum class Stage {
        SENT,
        PENDING,
        REJECTED,
        ACCEPTED
    }
}