package com.bluerose.jobee.ui.features.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.data.models.User
import com.bluerose.jobee.databinding.FragmentSigninBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.features.home.HomeFragment
import com.bluerose.jobee.ui.utils.ValidationResult

class SignInFragment : BaseFragment<FragmentSigninBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            val user = User(binding.usernameTextField.getText(), binding.emailTextField.getText())
            when (val result = user.validate()) {
                is ValidationResult.Success -> {
                    Singletons.repository.signInUser(user)
                    navigateTo(HomeFragment())
                }

                is ValidationResult.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}