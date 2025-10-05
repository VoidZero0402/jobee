package com.bluerose.jobee.abstractions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentOnAttachListener

fun <F : BaseFragment<*>> F.doOnAttach(fragmentManager: FragmentManager, action: (fragment: F) -> Unit): F {
    val fragmentRef = this
    fragmentManager.addFragmentOnAttachListener(object : FragmentOnAttachListener {
        override fun onAttachFragment(fragmentManager: FragmentManager, fragment: Fragment) {
            if (fragment === fragmentRef) {
                action(fragment)
                fragmentManager.removeFragmentOnAttachListener(this)
            }
        }
    })
    return this
}