package com.example.popupinpopup

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupWindow
import androidx.emoji2.emojipicker.EmojiPickerView
import com.example.popupinpopup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
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
            popupView.findViewById<EmojiPickerView>(R.id.emoji_picker_view).setOnEmojiPickedListener {
                binding.fab.text = it.emoji
                binding.fab.icon = null
                popup.dismiss()
            }
            popup.showAsDropDown(view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}