package ru.altf000.adapterdelegates.base

abstract class AdapterDelegateSelector {
    abstract fun getDelegate(item: DItem): DAdapter
}

class AdapterDelegateClassSelector : AdapterDelegateSelector() {

    private val delegatesMap = mutableMapOf<Class<out DItem>, Any>()

    fun addDelegate(clazz: Class<out DItem>, delegate: DAdapter) {
        delegatesMap[clazz] = delegate
    }

    fun addDelegateSelector(clazz: Class<out DItem>, delegateSelector: AdapterDelegateSelector) {
        delegatesMap[clazz] = delegateSelector
    }

    override fun getDelegate(item: DItem): DAdapter {
        val clazz = item::class.java
        val delegate = delegatesMap[clazz] ?: error("Could not find delegate for class: $clazz")
        return if (delegate is AdapterDelegateSelector) {
            delegate.getDelegate(item)
        } else {
            delegate as DAdapter
        }
    }
}