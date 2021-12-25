package ru.altf000.adapterdelegates.adapterdelegates

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CompositePagingAdapter(
    lifecycleScope: CoroutineScope,
    delegatesSelector: DSelector
) : PagingDataAdapter<DItem, BindingVH>(AdapterDelegatesDiffCallback()) {

    private val helper = CompositeAdapterHelper(delegatesSelector)
    private val updateStateFlow = onPagesUpdatedFlow.stateIn(lifecycleScope, SharingStarted.Lazily, Unit)

    val loadingState = loadStateFlow.map { state ->
        state.refresh is LoadState.Loading
    }

    val emptyState = loadStateFlow.combine(updateStateFlow) { state, _ ->
        state.refresh is LoadState.NotLoading && state.append.endOfPaginationReached && itemCount == 0
    }

    val errorState = loadStateFlow.map { state ->
        val refresh = state.refresh
        if (refresh is LoadState.Error) {
            refresh.error
        } else {
            null
        }
    }

    override fun getItemViewType(position: Int) = helper.getViewType(item(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = helper.create(parent, viewType)

    override fun onBindViewHolder(holder: BindingVH, position: Int) {
        helper.bind(holder, item(position), position, emptyList())
    }

    override fun onBindViewHolder(holder: BindingVH, position: Int, payloads: MutableList<Any>) {
        helper.bind(holder, item(position), position, payloads)
    }

    override fun onViewAttachedToWindow(holder: BindingVH) {
        helper.attached(holder)
    }

    override fun onViewDetachedFromWindow(holder: BindingVH) {
        helper.detached(holder)
    }

    override fun onViewRecycled(holder: BindingVH) {
        helper.recycled(holder)
    }

    private fun item(position: Int) = getItem(position) ?: error("Could not get item for $position position")
}
