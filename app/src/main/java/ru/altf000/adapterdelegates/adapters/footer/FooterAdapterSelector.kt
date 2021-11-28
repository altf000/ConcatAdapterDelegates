package ru.altf000.adapterdelegates.adapters.footer

import ru.altf000.adapterdelegates.base.AdapterDelegateSelector
import ru.altf000.adapterdelegates.items.FooterItem

class FooterAdapterSelector : AdapterDelegateSelector<FooterItem>() {

    override fun getDelegate(item: FooterItem) = if (item.text.contains("1")) {
        FooterAdapterDelegate()
    } else {
        FooterAdapterDelegateNew()
    }
}