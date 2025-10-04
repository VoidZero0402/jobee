package com.bluerose.jobee.abstractions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseRecyclerViewAdapter<T, VB : ViewBinding>(
    protected var data: MutableList<T>
) : RecyclerView.Adapter<BaseRecyclerViewAdapter<T, VB>.BaseViewHolder>() {

    private var inflateMethod: Method? = null

    inner class BaseViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    abstract fun onBindView(binding: VB, item: T, position: Int)

    @Suppress("UNCHECKED_CAST")
    private fun inflateBinding(inflater: LayoutInflater, parent: ViewGroup): VB {
        if (inflateMethod == null) {
            val superclass = javaClass.genericSuperclass
            require(superclass is ParameterizedType) {
                "BaseRecyclerViewAdapter must be directly subclassed with generic type arguments"
            }
            val viewBindingClass = superclass.actualTypeArguments[1] as Class<VB>
            inflateMethod = viewBindingClass.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
        }
        return inflateMethod!!.invoke(null, inflater, parent, false) as VB
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = inflateBinding(LayoutInflater.from(parent.context), parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindView(holder.binding, data[position], position)
    }

    override fun getItemCount(): Int = data.size
}
