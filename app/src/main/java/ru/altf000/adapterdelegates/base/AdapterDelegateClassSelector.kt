package ru.altf000.adapterdelegates.base

import androidx.viewbinding.ViewBinding

abstract class AdapterDelegateSelector<M : DItem> {
    abstract fun getDelegate(item: M): DAdapter
}

class AdapterDelegateClassSelector : AdapterDelegateSelector<DItem>() {

    private val delegatesMap = mutableMapOf<Class<out DItem>, Any>()

    fun addDelegate(clazz: Class<out DItem>, delegate: AdapterDelegate<out DItem, out ViewBinding>) {
        delegatesMap[clazz] = delegate
    }

    fun addDelegateSelector(clazz: Class<out DItem>, delegateSelector: AdapterDelegateSelector<out DItem>) {
        delegatesMap[clazz] = delegateSelector
    }

    override fun getDelegate(item: DItem): DAdapter {
        val clazz = item::class.java
        val delegate = delegatesMap[clazz] ?: error("Unsupported delegate for class: $clazz")
        return if (delegate is AdapterDelegateSelector<*>) {
            @Suppress("UNCHECKED_CAST")
            (delegate as AdapterDelegateSelector<DItem>).getDelegate(item)
        } else {
            delegate as DAdapter
        }
    }
}