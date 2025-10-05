package com.bluerose.jobee.ui.features.messages

import android.view.View
import com.bluerose.jobee.abstractions.BaseRecyclerViewAdapter
import com.bluerose.jobee.data.models.Chat
import com.bluerose.jobee.databinding.ItemCardChatBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter(
    chats: MutableList<Chat>,
    private val onChatActionListener: OnChatActionListener
) : BaseRecyclerViewAdapter<Chat, ItemCardChatBinding>(chats) {

    override fun onBindView(binding: ItemCardChatBinding, item: Chat, position: Int) {
        binding.companyName.text = item.company.name
        binding.companyLogo.setImageResource(item.company.logo)
        binding.message.text = item.lastMessage

        if (item.unreadMessageCount > 0) {
            binding.unreadMessageCount.apply {
                text = item.unreadMessageCount.toString()
                visibility = View.VISIBLE
            }
        }

        val date = Date(item.lastMessageTimestamp)
        val timeString = SimpleDateFormat("hh:mm a", Locale.US).format(date)

        binding.time.text = timeString

        binding.root.setOnClickListener {
            onChatActionListener.onChatClick(item)
        }
    }

    interface OnChatActionListener {
        fun onChatClick(chat: Chat)
    }
}