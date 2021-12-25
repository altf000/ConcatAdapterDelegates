package ru.altf000.adapterdelegates.adapterdelegates

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class CompositeAdapter(
    delegatesSelector: DSelector
) : ListAdapter<DItem, BindingVH>(AdapterDelegatesDiffCallback()) {

    private val helper = CompositeAdapterHelper(delegatesSelector)

    override fun getItemViewType(position: Int) = helper.getViewType(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = helper.create(parent, viewType)

    override fun onBindViewHolder(holder: BindingVH, position: Int) {
        helper.bind(holder, getItem(position), position, emptyList())
    }

    override fun onBindViewHolder(holder: BindingVH, position: Int, payloads: MutableList<Any>) {
        helper.bind(holder, getItem(position), position, payloads)
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
}
