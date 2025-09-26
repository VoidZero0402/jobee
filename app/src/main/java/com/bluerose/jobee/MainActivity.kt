package com.bluerose.jobee

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.bluerose.jobee.databinding.ActivityMainBinding
import com.bluerose.jobee.ui.utils.getThemeColor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val isSystemDarkMode: Boolean
        get() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(getThemeColor(R.attr.colorSurface)))
        setContentView(binding.root)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = !isSystemDarkMode
    }
}