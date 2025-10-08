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
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.utils.getThemeColor
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB

    protected val themeManager = Singletons.themeManager

    protected val isSystemDarkMode: Boolean
        get() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(getThemeColor(R.attr.colorSurface)))
        setContentView(binding.root)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = !isSystemDarkMode
        themeManager.applyTheme()
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

    fun navigateTo(fragment: BaseFragment<*>, args: Bundle? = null, addToBackStack: Boolean = true) {
        fragment.arguments = args
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    open fun onViewCreated() {}

    open fun onLayoutStateChanged(state: LayoutState) {}

    open fun getLayoutActionBar(): ActionBar? {
        return null
    }
}