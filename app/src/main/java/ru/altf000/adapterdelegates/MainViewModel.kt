package ru.altf000.adapterdelegates

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.altf000.adapterdelegates.adapterdelegates.DItem
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdelegates.items.FooterItem
import ru.altf000.adapterdelegates.items.HeaderItem

class MainViewModel : ViewModel() {

    private val _list1 = MutableStateFlow(
        mutableListOf<DItem>().apply {
            add(HeaderItem("List 1"))
            add(ContentItem("1"))
            add(FooterItem)
        }
    )
    val list1 = _list1.asStateFlow()

    private val _list2 = MutableStateFlow(
        mutableListOf<DItem>().apply {
            add(HeaderItem("List 2"))
            repeat(3) { add(ContentItem("${it + 1}")) }
            add(FooterItem)
        }
    )
    val list2 = _list2.asStateFlow()

    private val _list3 = MutableStateFlow<List<DItem>>(
        value = mutableListOf<DItem>().apply {
            add(HeaderItem("List 3"))
            add(ContentItem("3"))
            add(FooterItem)
        }
    )
    val list3 = _list3.asStateFlow()

    val pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE
        )
    ) { PagingDataSource() }.flow

    companion object {
        private const val PAGE_SIZE = 10
    }
}