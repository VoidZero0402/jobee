package com.bluerose.jobee.abstractions

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.getThemeColor
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected val isSystemDarkMode: Boolean
        get() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(getThemeColor(R.attr.colorSurface)))
        setContentView(binding.root)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = !isSystemDarkMode
        onViewCreated()
    }

    @Suppress("UNCHECKED_CAST")
    private fun inflateBinding(): VB {
        val superclass = javaClass.genericSuperclass
        require(superclass is ParameterizedType) {
            "BaseActivity must be directly subclassed with generic type arguments"
        }
        val viewBindingClass = superclass.actualTypeArguments[0] as Class<VB>
        val inflateMethod = viewBindingClass.getMethod("inflate", LayoutInflater::class.java)
        return inflateMethod.invoke(null, layoutInflater) as VB
    }

    fun navigateTo(fragment: BaseFragment<*>, args: Bundle? = null) {
        fragment.arguments = args
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    open fun onViewCreated() {}

    open fun onLayoutStateChanged(state: LayoutState) {}
}