package ru.altf000.adapterdelegates.items

import ru.altf000.adapterdelegates.base.DItem
import java.util.*

data class FooterItem(val text: String) : DItem() {
    override val identifier: Any = UUID.randomUUID().toString()
    override val data: Any = text
}