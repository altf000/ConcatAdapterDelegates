package ru.altf000.adapterdelegates.base

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias DAdapter = AdapterDelegate<*, *>

abstract class AdapterDelegate<M, VB> where M : DItem, VB : ViewBinding {
    abstract val viewType: Int
    abstract val itemClass: Class<out M>
    open fun onViewAttachedToWindow(binding: VB) {}
    abstract fun createBinding(parent: ViewGroup): ViewBinding
    abstract fun onBind(item: M, binding: VB)
    abstract fun onUnbind(binding: VB)
    open fun onViewDetachedFromWindow(binding: VB) {}
}