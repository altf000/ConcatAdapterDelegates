package ru.altf000.adapterdelegates.adapters.footer

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.altf000.adapterdelegates.base.AdapterDelegate
import ru.altf000.adapterdelegates.items.FooterItem
import ru.altf000.adapterdepegates.R
import ru.altf000.adapterdepegates.databinding.LayoutItemFooterNewBinding

class FooterAdapterDelegateNew : AdapterDelegate<FooterItem, LayoutItemFooterNewBinding>() {

    override val viewType = R.layout.layout_item_footer_new
    override val itemClass = FooterItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemFooterNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: FooterItem, binding: LayoutItemFooterNewBinding) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemFooterNewBinding) {
        binding.text.text = null
    }
}