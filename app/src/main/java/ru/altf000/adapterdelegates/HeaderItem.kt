package ru.altf000.adapterdelegates

import ru.altf000.adapterdelegates.base.DItem
import ru.altf000.adapterdepegates.R
import java.util.*

data class HeaderItem(
    val text: String
) : DItem {

    override val viewType: Int = R.layout.layout_item_header
    override val identifier: Any = UUID.randomUUID().toString()
    override val data: Any = text
}