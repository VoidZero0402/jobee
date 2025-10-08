package com.bluerose.jobee.ui.features.messages

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.Chat
import com.bluerose.jobee.databinding.FragmentChatBinding
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.BundleKeys
import com.bluerose.jobee.ui.features.home.HomeFragment
import com.bluerose.jobee.ui.utils.parcelable

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR,
        lazy {
            ActionBar.Config(
                actions = listOf(
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_phone),
                        resources.getString(R.string.cd_call),
                        null
                    ),
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_videocamera),
                        resources.getString(R.string.cd_video_call),
                        null
                    )
                )
            )
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chat = arguments?.parcelable<Chat>(BundleKeys.CHAT)

        if (chat != null) {
            supportActionBar!!.apply {
                title = chat.company.name
                setLogo(ContextCompat.getDrawable(context, chat.company.logo))
            }
        } else {
            navigateTo(HomeFragment())
        }
    }
}