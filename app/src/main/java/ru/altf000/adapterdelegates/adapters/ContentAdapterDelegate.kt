package ru.altf000.adapterdelegates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.base.AdapterDelegate
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemContentBinding

class ContentAdapterDelegate : AdapterDelegate<ContentItem, LayoutItemContentBinding>() {

    override val viewType = R.layout.layout_item_content

    override fun createBinding(parent: ViewGroup) =
        LayoutItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: ContentItem, binding: LayoutItemContentBinding) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemContentBinding) {
        binding.text.text = null
    }
}