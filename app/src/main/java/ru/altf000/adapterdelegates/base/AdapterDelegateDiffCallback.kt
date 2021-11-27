package ru.altf000.adapterdelegates.base

import androidx.recyclerview.widget.DiffUtil
import java.util.*

class AdapterDelegateDiffCallback : DiffUtil.ItemCallback<DItem>() {

    override fun areItemsTheSame(oldItem: DItem, newItem: DItem): Boolean =
        oldItem::class == newItem::class && oldItem.identifier == newItem.identifier

    override fun areContentsTheSame(oldItem: DItem, newItem: DItem): Boolean =
        Objects.equals(oldItem.data, newItem.data)

    override fun getChangePayload(oldItem: DItem, newItem: DItem): Any = true
}