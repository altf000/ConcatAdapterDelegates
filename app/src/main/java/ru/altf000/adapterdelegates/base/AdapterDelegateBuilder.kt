package ru.altf000.adapterdelegates.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdapterDelegateBuilder {

    private val adapterItems = mutableListOf<Flow<List<DItem>>>()

    fun addItems(items: Flow<List<DItem>>): AdapterDelegateBuilder {
        adapterItems.add(items)
        return this
    }

    fun build(lifecycleOwner: LifecycleOwner, selector: AdapterDelegateClassSelector): ConcatAdapter {
        val adapter = ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())
        adapterItems.forEach { flow ->
            val compositeAdapter = CompositeAdapter(selector)
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