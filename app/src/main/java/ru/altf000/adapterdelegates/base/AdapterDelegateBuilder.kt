package ru.altf000.adapterdelegates.base

import androidx.lifecycle.*
import androidx.recyclerview.widget.ConcatAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdapterDelegateBuilder {

    private val adapterDelegates = mutableListOf<DAdapter>()
    private val adapterItems = mutableListOf<Flow<List<DItem>>>()

    fun addAdapter(delegate: AdapterDelegate<*, *>): AdapterDelegateBuilder {
        @Suppress("UNCHECKED_CAST")
        adapterDelegates.add(delegate as DAdapter)
        return this
    }

    fun addItems(items: Flow<List<DItem>>): AdapterDelegateBuilder {
        adapterItems.add(items)
        return this
    }

    fun build(lifecycleOwner: LifecycleOwner): ConcatAdapter {
        val adapter = ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())
        adapterItems.forEach { flow ->
            val compositeAdapter = CompositeAdapter(adapterDelegates)
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    flow.collectLatest { items ->
                        compositeAdapter.submitList(items)
                    }
                }
            }
            adapter.addAdapter(compositeAdapter)
        }
        return adapter
    }
}