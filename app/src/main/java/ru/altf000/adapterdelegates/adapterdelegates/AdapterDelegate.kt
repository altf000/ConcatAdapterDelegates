package ru.altf000.adapterdelegates.adapterdelegates

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias DAdapter = AdapterDelegate<*, *>

abstract class AdapterDelegate<M, VB> where M : DItem, VB : ViewBinding {

    abstract val viewType: Int
    abstract val itemClass: Class<out M>

    open fun onAttached(binding: VB, position: Int) {}
    abstract fun createBinding(parent: ViewGroup): ViewBinding
    abstract fun onBind(item: M, binding: VB, position: Int, payloads: List<Any>)
    abstract fun onUnbind(binding: VB)
    open fun onDetached(binding: VB, position: Int) {}
}