package ru.altf000.adapterdelegates.base

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class CompositeAdapter(private val delegates: List<DAdapter>) :
    ListAdapter<DItem, BindingVH>(AdapterDelegateDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingVH {
        return BindingViewHolder(getDelegate(viewType).createBinding(parent))
    }

    override fun onBindViewHolder(holder: BindingVH, position: Int) {
        getDelegate(holder.itemViewType).onBind(getItem(position), holder.binding)
    }

    override fun onBindViewHolder(holder: BindingVH, position: Int, payloads: MutableList<Any>) {
        getDelegate(holder.itemViewType).onBind(getItem(position), holder.binding)
    }

    override fun onViewAttachedToWindow(holder: BindingVH) {
        getDelegate(holder.itemViewType).onViewAttachedToWindow(holder.binding)
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: BindingVH) {
        getDelegate(holder.itemViewType).onViewDetachedFromWindow(holder.binding)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: BindingVH) {
        getDelegate(holder.itemViewType).onUnbind(holder.binding)
        super.onViewRecycled(holder)
    }

    private fun getDelegate(viewType: Int): DAdapter {
        return delegates.find { it.viewType == viewType } ?: error("Unsupported viewType: $viewType")
    }
}