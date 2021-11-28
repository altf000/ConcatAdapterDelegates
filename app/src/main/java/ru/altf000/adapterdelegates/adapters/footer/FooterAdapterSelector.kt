package ru.altf000.adapterdelegates.adapters.footer

import ru.altf000.adapterdelegates.base.AdapterDelegateSelector
import ru.altf000.adapterdelegates.base.DAdapter
import ru.altf000.adapterdelegates.base.DItem
import ru.altf000.adapterdelegates.items.FooterItem

class FooterAdapterSelector : AdapterDelegateSelector() {

    override fun getDelegate(item: DItem): DAdapter {
        if (item is FooterItem) {
            return if (item.text.contains("1")) {
                FooterAdapterDelegate()
            } else {
                FooterAdapterDelegateNew()
            }
        }
        error("Unsupported item class: ${item::class.java} for ${this::class.java}")
    }
}