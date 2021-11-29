package ru.altf000.adapterdelegates.base

import androidx.viewbinding.ViewBinding

fun delegateClassSelector(block: AdapterDelegateClassSelector.() -> Unit): AdapterDelegateClassSelector {
    val selector = AdapterDelegateClassSelector()
    block(selector)
    return selector
}

abstract class AdapterDelegateSelector<M : DItem> {
    abstract fun getDelegate(item: M): DAdapter
}

class AdapterDelegateClassSelector : AdapterDelegateSelector<DItem>() {

    private val delegatesList = mutableListOf<Any>()
    private val cachedDelegatesMap = mutableMapOf<Class<out DItem>, DAdapter>()

    fun delegate(delegate: AdapterDelegate<out DItem, out ViewBinding>) {
        if (!delegatesList.contains(delegate)) {
            delegatesList.add(delegate)
        }
    }

    fun delegateSelector(delegateSelector: AdapterDelegateSelector<out DItem>) {
        if (!delegatesList.contains(delegateSelector)) {
            delegatesList.add(delegateSelector)
        }
    }

    override fun getDelegate(item: DItem): DAdapter {
        val itemClass = item::class.java
        cachedDelegatesMap[itemClass]?.let { delegate ->
            return delegate
        }
        delegatesList.forEach {
            getDelegate(item, it)?.let { delegate ->
                return delegate
            }
        }
        error("Could not find delegate for class ${item::class.java}")
    }

    private fun getDelegate(item: DItem, delegate: Any): DAdapter? {
        val itemClass = item::class.java
        return when (delegate) {
            is AdapterDelegateSelector<*> -> {
                @Suppress("UNCHECKED_CAST")
                val selector = delegate as AdapterDelegateSelector<DItem>
                val innerDelegate = selector.getDelegate(item)
                if (innerDelegate.itemClass == itemClass) {
                    innerDelegate
                } else {
                    null
                }
            }
            is DAdapter -> if (delegate.itemClass == itemClass) {
                cachedDelegatesMap[itemClass] = delegate
                delegate
            } else {
                null
            }
            else -> null
        }
    }
}