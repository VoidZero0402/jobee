package com.bluerose.jobee.abstractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.viewbinding.ViewBinding
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.AnimationDuration
import java.lang.reflect.ParameterizedType

open class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    open val layoutState = LayoutState()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        enterTransition = Fade().apply {
            duration = AnimationDuration.MEDIUM.duration.toLong()
        }
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? BaseActivity<*>)?.onLayoutStateChanged(layoutState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Suppress("UNCHECKED_CAST")
    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        val superclass = javaClass.genericSuperclass
        require(superclass is ParameterizedType) {
            "BaseFragment must be directly subclassed with generic type arguments"
        }
        val viewBindingClass = superclass.actualTypeArguments[0] as Class<VB>
        val inflateMethod = viewBindingClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return inflateMethod.invoke(null, inflater, container, false) as VB
    }

    fun navigateTo(fragment: BaseFragment<*>, args: Bundle? = null, addToBackStack: Boolean = true) {
        fragment.arguments = args
        val transaction = parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}