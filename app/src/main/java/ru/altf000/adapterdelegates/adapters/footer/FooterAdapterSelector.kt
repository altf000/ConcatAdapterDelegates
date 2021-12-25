package ru.altf000.adapterdelegates.adapters.footer

import ru.altf000.adapterdelegates.adapterdelegates.AdapterDelegatesSelector
import ru.altf000.adapterdelegates.items.FooterItem

class FooterAdapterSelector : AdapterDelegatesSelector<FooterItem>() {

    override fun getDelegate(item: FooterItem) = if (item.text.contains("1")) {
        FooterAdapterDelegate()
    } else {
        FooterAdapterDelegateNew()
    }
}