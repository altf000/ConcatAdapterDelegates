package ru.altf000.adapterdelegates.adapterdelegates

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

typealias BindingVH = BindingViewHolder<*>

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)