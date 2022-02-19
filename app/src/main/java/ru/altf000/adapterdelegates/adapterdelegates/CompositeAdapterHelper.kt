package ru.altf000.adapterdelegates.adapterdelegates

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

class CompositeAdapterHelper(private val delegatesSelector: DSelector) {

    private val delegatesMap = mutableMapOf<Int, DAdapter>()

    fun getViewType(item: DItem): Int {
        val delegate = delegatesSelector.getDelegate(item)
        delegatesMap[delegate.viewType] = delegate
        return delegate.viewType
    }

    fun create(parent: ViewGroup, viewType: Int) = BindingViewHolder(getDelegate(viewType).createBinding(parent))

    fun bind(holder: BindingVH, item: DItem, position: Int, payloads: List<Any>) {
        getDelegate(holder.itemViewType).onBind(item, holder.binding, position, payloads)
    }

    fun attached(holder: BindingVH) {
        getDelegate(holder.itemViewType).onAttached(holder.binding, holder.bindingAdapterPosition)
    }

    fun detached(holder: BindingVH) {
        getDelegate(holder.itemViewType).onDetached(holder.binding, holder.bindingAdapterPosition)
    }

    fun recycled(holder: BindingVH) {
        getDelegate(holder.itemViewType).onUnbind(holder.binding)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getDelegate(viewType: Int) = delegatesMap[viewType] as AdapterDelegate<DItem, ViewBinding>
}
