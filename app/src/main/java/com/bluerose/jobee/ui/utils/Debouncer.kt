package com.bluerose.jobee.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer(private val coroutineScope: CoroutineScope, private val delayMillis: Long = 500) {
    private var job: Job? = null

    operator fun invoke(action: () -> Unit) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(delayMillis)
            action()
        }
    }
}