package ru.altf000.adapterdelegates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.altf000.adapterdelegates.base.AdapterDelegateItem
import ru.altf000.adapterdelegates.items.ContentItem
import ru.altf000.adapterdelegates.items.FooterItem
import ru.altf000.adapterdelegates.items.HeaderItem

class MainViewModel : ViewModel() {

    private val _headerItems = MutableStateFlow<List<AdapterDelegateItem>>(
        emptyList()
    ).apply {
        value = mutableListOf<AdapterDelegateItem>().apply {
            add(HeaderItem("HeaderItems, HeaderViewHolder 1"))
            add(HeaderItem("HeaderItems, HeaderViewHolder 2"))
            add(HeaderItem("HeaderItems, HeaderViewHolder 3"))
            add(ContentItem("HeaderItems, ContentViewHolder 4"))
        }
    }
    val headerItems = _headerItems.asStateFlow()

    private val _contentItems = MutableStateFlow<List<AdapterDelegateItem>>(
        emptyList()
    ).apply {
        value = mutableListOf<AdapterDelegateItem>().apply {
            repeat(3) {
                add(ContentItem("ContentItems, ContentViewHolder ${it + 1}"))
            }
        }
    }
    val contentItems = _contentItems.asStateFlow()

    private val _footerItems = MutableStateFlow<List<AdapterDelegateItem>>(
        emptyList()
    ).apply {
        value = mutableListOf<AdapterDelegateItem>().apply {
            add(FooterItem("FooterItems, FooterViewHolder 1"))
            add(FooterItem("FooterItems, FooterViewHolder 2"))
            add(FooterItem("FooterItems, FooterViewHolder 3"))
            add(ContentItem("FooterItems, ContentViewHolder 4"))
        }
    }
    val footerItems = _footerItems.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(2000)
                _headerItems.value = _headerItems.value.shuffled()
            }
        }
        viewModelScope.launch {
            while (true) {
                delay(5000)
                _contentItems.value = _contentItems.value.shuffled()
            }
        }
        viewModelScope.launch {
            while (true) {
                delay(8000)
                _footerItems.value = _footerItems.value.shuffled()
            }
        }
    }
}