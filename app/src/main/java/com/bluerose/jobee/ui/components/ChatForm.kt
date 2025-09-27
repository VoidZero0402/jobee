package com.bluerose.jobee.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.LayoutChatFormBinding

private typealias OnActionClickListener = ((view: View) -> Unit)?

class ChatForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutChatFormBinding.inflate(LayoutInflater.from(context), this)
    private var isMessageTextFieldEmpty = true

    private var onVoiceRecordActionClicked: OnActionClickListener = null
    private var onSendActionClicked: OnActionClickListener = null

    data class FloatActionStateConfig(
        val icon: Drawable?,
        val contentDescription: String
    )

    init {
        setupListeners()
    }

    private fun setupListeners() {
        binding.messageTextField.addTextChangedListener {
            val isEditTextEmpty = it.toString().isEmpty()
            if (isMessageTextFieldEmpty != isEditTextEmpty) {
                isMessageTextFieldEmpty = isEditTextEmpty
                applyFloatActionState()
            }
        }
        binding.floatAction.setOnClickListener {
            if (isMessageTextFieldEmpty) onVoiceRecordActionClicked?.invoke(it) else onSendActionClicked?.invoke(it)
        }
    }

    private fun getFloatActionStateConfig(): FloatActionStateConfig {
        return when {
            isMessageTextFieldEmpty -> FloatActionStateConfig(
                ContextCompat.getDrawable(context, R.drawable.ic_microphone_bold),
                resources.getString(R.string.cd_record_voice)
            )

            else -> FloatActionStateConfig(
                ContextCompat.getDrawable(context, R.drawable.ic_plain_bold),
                resources.getString(R.string.cd_send_message)
            )
        }
    }

    private fun applyFloatActionState() {
        val (icon, description) = getFloatActionStateConfig()
        binding.floatAction.apply {
            setImageDrawable(icon)
            contentDescription = description
        }
    }

    fun setOnVoiceRecordActionClickListener(l: OnActionClickListener) {
        onVoiceRecordActionClicked = l
    }

    fun setOnSendActionClickListener(l: OnActionClickListener) {
        onSendActionClicked = l
    }

    fun setOnEmojiActionClickListener(l: OnActionClickListener) {
        binding.emojiAction.setOnClickListener(l)
    }

    fun setOnCameraActionClickListener(l: OnActionClickListener) {
        binding.cameraAction.setOnClickListener(l)
    }
}