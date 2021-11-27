package ru.altf000.adapterdelegates.base

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias DAdapter = AdapterDelegate<DItem, ViewBinding>

abstract class AdapterDelegate<M : DItem, VB : ViewBinding> {
    abstract val viewType: Int
    open fun onViewAttachedToWindow(binding: VB) {}
    abstract fun createBinding(parent: ViewGroup): ViewBinding
    abstract fun onBind(item: M, binding: VB)
    abstract fun onUnbind(binding: VB)
    open fun onViewDetachedFromWindow(binding: VB) {}
}