package com.bluerose.jobee.ui.features.messages

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.Chat
import com.bluerose.jobee.databinding.FragmentMessagesBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.utils.SpaceItemDecoration

class MessagesFragment : BaseFragment<FragmentMessagesBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR_AND_BOTTOM_NAV,
        lazy {
            ActionBar.Config(
                title = resources.getString(R.string.action_bar_messages),
                isNavigationActionVisible = false,
                logo = ContextCompat.getDrawable(requireContext(), R.drawable.logo_jobee),
                actions = listOf(
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_magnifier),
                        resources.getString(R.string.cd_search),
                        null
                    )
                )
            )
        },
        NavItemPositions.MESSAGES
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onChatActionListener = object : ChatAdapter.OnChatActionListener {
            override fun onChatClick(chat: Chat) {}
        }

        binding.chatsRecycler.apply {
            adapter = ChatAdapter(Singletons.repository.getChats().toMutableList(), onChatActionListener)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.content_gap_sm)))
        }
    }
}