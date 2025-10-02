package com.bluerose.jobee.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val name: String,
    val logo: Int,
    val category: String,
) : Parcelable