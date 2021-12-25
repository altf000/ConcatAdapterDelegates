package ru.altf000.adapterdelegates.items

import ru.altf000.adapterdelegates.adapterdelegates.DItem

data class HeaderItem(val text: String) : DItem() {
    override val identifier = text.hashCode()
    override val data: Any = text
}