package ru.altf000.adapterdelegates.items

import ru.altf000.adapterdelegates.adapterdelegates.DItem
import java.util.*

data class HeaderItem(val text: String) : DItem() {
    override val identifier: Any = UUID.randomUUID().toString()
    override val data: Any = text
}