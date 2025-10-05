package com.bluerose.jobee.ui.features.notifications

import android.view.View
import com.bluerose.jobee.abstractions.BaseRecyclerViewAdapter
import com.bluerose.jobee.data.models.Notification
import com.bluerose.jobee.databinding.ItemCardNotificationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationAdapter(
    notifications: MutableList<Notification>
) : BaseRecyclerViewAdapter<Notification, ItemCardNotificationBinding>(notifications) {

    override fun onBindView(binding: ItemCardNotificationBinding, item: Notification, position: Int) {
        binding.title.text = item.title
        binding.description.text = item.description

        if (item.isUnread) {
            binding.unreadTag.visibility = View.VISIBLE
        }

        val date = Date(item.timestamp)

        val dateString = SimpleDateFormat("dd MMM, yyyy", Locale.US).format(date)
        val timeString = SimpleDateFormat("hh:mm a", Locale.US).format(date)

        binding.date.text = dateString
        binding.time.text = timeString
    }
}