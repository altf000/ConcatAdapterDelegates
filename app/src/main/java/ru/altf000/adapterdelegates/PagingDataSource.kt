package ru.altf000.adapterdelegates

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import ru.altf000.adapterdelegates.adapterdelegates.DItem
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdelegates.items.HeaderItem

class PagingDataSource : PagingSource<Int, DItem>() {

    private var currentPage: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DItem> {
        delay(2000)
        return LoadResult.Page(
            data = mutableListOf<DItem>().apply {
                if (currentPage == 0) {
                    add(HeaderItem("PagingList"))
                }
                (0 until params.loadSize).forEach {
                    add(ContentItem("page = ${currentPage}, item = $it"))
                }
            },
            prevKey = null,
            nextKey = currentPage++
        )
    }

    override fun getRefreshKey(state: PagingState<Int, DItem>) = 0
}