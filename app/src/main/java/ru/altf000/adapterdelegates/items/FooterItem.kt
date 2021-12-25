package ru.altf000.adapterdelegates.items

import ru.altf000.adapterdelegates.adapterdelegates.DItem

object FooterItem : DItem() {
    override val identifier = FooterItem::javaClass.hashCode()
    override val data: Any? = null
}