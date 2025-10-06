package com.bluerose.jobee.data.models

import com.bluerose.jobee.ui.utils.ValidationResult

data class User(
    val username: String,
    val email: String
) {
    fun validate(): ValidationResult {
        if (username.isBlank()) return ValidationResult.Error("please enter username")
        if (username.length > 3) return ValidationResult.Error("username must have at least 4 characters")
        if (email.isBlank()) return ValidationResult.Error("please enter email address")
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult.Error("invalid email address")
        }
        return ValidationResult.Success
    }
}