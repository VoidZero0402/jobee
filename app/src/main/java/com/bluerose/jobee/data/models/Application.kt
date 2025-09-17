package com.bluerose.jobee.data.models

data class Application(
    val job: Job,
    val stage: Stage
) {
    enum class Stage {
        Sent,
        Pending,
        Rejected,
        Accepted
    }
}
