package ru.altf000.adapterdelegates.adapterdelegates

import androidx.viewbinding.ViewBinding

typealias DSelector = AdapterDelegatesSelector<DItem>

abstract class AdapterDelegatesSelector<M : DItem> {
    abstract val itemClass: Class<M>
    abstract fun getDelegate(item: M): DAdapter
}

class AdapterDelegatesSelectorImpl : AdapterDelegatesSelector<DItem>() {

    override val itemClass = DItem::class.java

    private val delegatesList = mutableListOf<Any>()
    private val cachedDelegatesMap = mutableMapOf<Class<out DItem>, DAdapter>()

    fun addDelegate(delegate: AdapterDelegate<out DItem, out ViewBinding>) {
        if (!delegatesList.contains(delegate)) {
            delegatesList.add(delegate)
        }
    }

    fun addDelegateSelector(delegateSelector: AdapterDelegatesSelector<out DItem>) {
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
        return FALLBACK_ADAPTER
    }

    private fun getDelegate(item: DItem, delegate: Any): DAdapter? {
        val itemClass = item::class.java
        return when (delegate) {
            is AdapterDelegatesSelector<*> -> {
                @Suppress("UNCHECKED_CAST")
                val selector = delegate as DSelector
                if (delegate.itemClass == itemClass) {
                    selector.getDelegate(item)
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

    companion object {
        private val FALLBACK_ADAPTER = FallbackAdapterDelegate()
    }
}