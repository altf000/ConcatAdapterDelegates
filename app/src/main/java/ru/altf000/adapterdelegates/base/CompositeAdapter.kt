package ru.altf000.adapterdelegates.base

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

class CompositeAdapter(private val delegateSelector: AdapterDelegateSelector<DItem>) :
    ListAdapter<DItem, BindingVH>(AdapterDelegateDiffCallback()) {

    private val delegatesMap = mutableMapOf<Int, DAdapter>()

    override fun getItemViewType(position: Int) = getViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(getDelegate(viewType).createBinding(parent))

    override fun onBindViewHolder(holder: BindingVH, position: Int) {
        getDelegate(holder.itemViewType).onBind(getItem(position), holder.binding)
    }

    override fun onBindViewHolder(holder: BindingVH, position: Int, payloads: MutableList<Any>) {
        getDelegate(holder.itemViewType).onBind(getItem(position), holder.binding)
    }

    override fun onViewAttachedToWindow(holder: BindingVH) {
        getDelegate(holder.itemViewType).onViewAttachedToWindow(holder.binding)
    }

    override fun onViewDetachedFromWindow(holder: BindingVH) {
        getDelegate(holder.itemViewType).onViewDetachedFromWindow(holder.binding)
    }

    override fun onViewRecycled(holder: BindingVH) {
        getDelegate(holder.itemViewType).onUnbind(holder.binding)
    }

    private fun getViewType(position: Int): Int {
        val item = getItem(position)
        val delegate = delegateSelector.getDelegate(item)
        delegatesMap[delegate.viewType] = delegate
        return delegate.viewType
    }

    @Suppress("UNCHECKED_CAST")
    private fun getDelegate(viewType: Int) =
        delegatesMap[viewType] as? AdapterDelegate<DItem, ViewBinding>
            ?: error("Could not find delegate for viewType $viewType")
}