package com.bluerose.jobee.ui.features.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.User
import com.bluerose.jobee.databinding.FragmentProfileBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.features.settings.SettingsFragment
import com.bluerose.jobee.ui.utils.ValidationResult

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR_AND_BOTTOM_NAV,
        lazy {
            ActionBar.Config(
                title = resources.getString(R.string.action_bar_profile),
                actions = listOf(
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_settings),
                        resources.getString(R.string.cd_settings)
                    ) {
                        navigateTo(SettingsFragment())
                    }
                )
            )
        },
        NavItemPositions.PROFILE
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val (username, email) = Singletons.repository.getUser()
        binding.usernameText.text = username
        binding.emailText.text = email
        binding.usernameTextField.setText(username)
        binding.emailTextField.setText(email)

        binding.saveProfileButton.setOnClickListener {
            val user = User(binding.usernameTextField.getText(), binding.emailTextField.getText())
            when (val result = user.validate()) {
                is ValidationResult.Success -> {
                    Singletons.repository.signInUser(user)
                    binding.usernameText.text = user.username
                    binding.emailText.text = user.email
                    Toast
                        .makeText(context, resources.getString(R.string.message_profile_saved), Toast.LENGTH_SHORT)
                        .show()
                }

                is ValidationResult.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}