package ru.altf000.adapterdelegates.adapters.content

import ru.altf000.adapterdelegates.adapterdelegates.AdapterDelegatesSelector
import ru.altf000.adapterdelegates.adapterdelegates.FallbackAdapterDelegate
import ru.altf000.adapterdelegates.items.ContentItem

class ContentAdapterDelegateSelector : AdapterDelegatesSelector<ContentItem>() {

    override val itemClass = ContentItem::class.java

    override fun getDelegate(item: ContentItem) = when {
        item.text.contains("1") -> ContentAdapterDelegate1()
        item.text.contains("2") -> ContentAdapterDelegate2()
        item.text.contains("3") -> ContentAdapterDelegate3()
        else -> FallbackAdapterDelegate()
    }
}