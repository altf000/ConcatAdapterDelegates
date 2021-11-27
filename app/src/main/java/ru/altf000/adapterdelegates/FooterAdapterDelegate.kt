package ru.altf000.adapterdelegates

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.base.AdapterDelegate
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemFooterBinding

class FooterAdapterDelegate : AdapterDelegate<FooterItem, LayoutItemFooterBinding>() {

    override val viewType = R.layout.layout_item_footer

    override fun createBinding(parent: ViewGroup) = LayoutItemFooterBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )

    override fun onBind(item: FooterItem, binding: LayoutItemFooterBinding) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemFooterBinding) {
        binding.text.text = null
    }
}