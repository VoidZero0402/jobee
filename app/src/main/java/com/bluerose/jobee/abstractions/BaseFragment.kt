package com.bluerose.jobee.abstractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class BaseFragment<VB : ViewBinding> : Fragment() {
    private lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflateBinding(inflater, container)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val viewBindingClass = superclass.actualTypeArguments[0] as Class<VB>
        val inflateMethod = viewBindingClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return inflateMethod.invoke(null, inflater, container, true) as VB
    }
}