package ru.altf000.adapterdelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.base.AdapterDelegate
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemHeaderBinding

class HeaderAdapterDelegate : AdapterDelegate<HeaderItem, LayoutItemHeaderBinding>() {

    override val viewType = R.layout.layout_item_header

    override fun createBinding(parent: ViewGroup) = LayoutItemHeaderBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    override fun onBind(item: HeaderItem, binding: LayoutItemHeaderBinding) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemHeaderBinding) {
        binding.text.text = null
    }
}