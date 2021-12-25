package ru.altf000.adapterdelegates.adapters.content

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.adapterdelegates.AdapterDelegate
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemContent1Binding

class ContentAdapterDelegate1 : AdapterDelegate<ContentItem, LayoutItemContent1Binding>() {

    override val viewType = R.layout.layout_item_content1
    override val itemClass = ContentItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemContent1Binding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: ContentItem, binding: LayoutItemContent1Binding, position: Int, payloads: List<Any>) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemContent1Binding) {
        binding.text.text = null
    }
}