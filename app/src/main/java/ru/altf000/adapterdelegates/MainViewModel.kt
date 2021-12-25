package ru.altf000.adapterdelegates

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdelegates.items.HeaderItem

class MainViewModel : ViewModel() {

    private val _firstList = MutableStateFlow(
        listOf(
            HeaderItem("firstList, HeaderViewHolder"),
            ContentItem("firstList, ContentViewHolder")
        )
    )
    val firstList = _firstList.asStateFlow()

    private val _secondList = MutableStateFlow(
        listOf(
            HeaderItem("secondList, HeaderViewHolder"),
            ContentItem("secondList, ContentViewHolder"),
            HeaderItem("secondList, HeaderViewHolder"),
            ContentItem("secondList, ContentViewHolder")
        )
    )
    val secondList = _secondList.asStateFlow()

    private val _thirdList = MutableStateFlow(
        listOf(
            HeaderItem("thirdList, HeaderViewHolder"),
            ContentItem("thirdList, ContentViewHolder")
        )
    )
    val thirdList = _thirdList.asStateFlow()
}