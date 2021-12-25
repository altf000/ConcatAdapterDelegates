package ru.altf000.adapterdelegates.adapters.content

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.adapterdelegates.AdapterDelegate
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemContent2Binding

class ContentAdapterDelegate2 : AdapterDelegate<ContentItem, LayoutItemContent2Binding>() {

    override val viewType = R.layout.layout_item_content2
    override val itemClass = ContentItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemContent2Binding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: ContentItem, binding: LayoutItemContent2Binding, position: Int, payloads: List<Any>) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemContent2Binding) {
        binding.text.text = null
    }
}