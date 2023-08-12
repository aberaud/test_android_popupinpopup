package com.example.popupinpopup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.emoji2.emojipicker.EmojiPickerView
import androidx.fragment.app.Fragment
import com.example.popupinpopup.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener(this::openEmojiPicker)

        binding.buttonSecond.setOnLongClickListener {
            // First popup
            val popupView: View = layoutInflater.inflate(R.layout.popup1, null)
            PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                resources.getDimensionPixelSize(R.dimen.popup1_height),
                false
            ).apply {
                elevation = resources.getDimension(R.dimen.popup_elevation)
                isOutsideTouchable = true
            }.showAsDropDown(it)

            // Second (nested) popup
            popupView.findViewById<Button>(R.id.button_second)
                .setOnClickListener(this::openEmojiPicker)
            true
        }
    }
    private fun openEmojiPicker(anchor: View) {
        val popupView: View = layoutInflater.inflate(R.layout.popup2, null)
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popup = PopupWindow(
            popupView,
            resources.getDimensionPixelSize(R.dimen.popup2_height),
            popupView.measuredHeight,
            true
        ).apply {
            elevation = resources.getDimension(R.dimen.popup_elevation)
            isOutsideTouchable = true
        }
        popup.showAsDropDown(anchor)
        popupView.findViewById<EmojiPickerView>(R.id.emoji_picker_view).setOnEmojiPickedListener {
            (anchor as? TextView)?.text = it.emoji
            popup.dismiss()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}