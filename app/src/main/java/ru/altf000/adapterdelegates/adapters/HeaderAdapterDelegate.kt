package ru.altf000.adapterdelegates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.adapterdelegates.AdapterDelegate
import ru.altf000.adapterdelegates.items.HeaderItem
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemHeaderBinding

class HeaderAdapterDelegate : AdapterDelegate<HeaderItem, LayoutItemHeaderBinding>() {

    override val viewType = R.layout.layout_item_header
    override val itemClass = HeaderItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: HeaderItem, binding: LayoutItemHeaderBinding, position: Int, payloads: List<Any>) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemHeaderBinding) {
        binding.text.text = null
    }
}