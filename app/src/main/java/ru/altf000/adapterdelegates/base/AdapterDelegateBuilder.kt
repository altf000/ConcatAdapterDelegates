package ru.altf000.adapterdelegates.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun adapterDelegates(
    lifecycleOwner: LifecycleOwner,
    classSelector: AdapterDelegateClassSelector,
    block: AdapterDelegateBuilder.() -> Unit
): ConcatAdapter {
    val adapterDelegate = AdapterDelegateBuilder(lifecycleOwner, classSelector)
    block(adapterDelegate)
    return adapterDelegate.build()
}

class AdapterDelegateBuilder(
    private val lifecycleOwner: LifecycleOwner,
    private val selector: AdapterDelegateClassSelector
) {

    private val adapterItems = mutableListOf<Flow<List<DItem>>>()

    fun items(vararg items: Flow<List<DItem>>) {
        items.forEach {
            adapterItems.add(it)
        }
    }

    fun build(): ConcatAdapter {
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