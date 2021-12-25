package ru.altf000.adapterdelegates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.adapterdelegates.AdapterDelegate
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemContentBinding

class ContentAdapterDelegate : AdapterDelegate<ContentItem, LayoutItemContentBinding>() {

    override val viewType = R.layout.layout_item_content
    override val itemClass = ContentItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: ContentItem, binding: LayoutItemContentBinding, position: Int, payloads: List<Any>) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemContentBinding) {
        binding.text.text = null
    }
}