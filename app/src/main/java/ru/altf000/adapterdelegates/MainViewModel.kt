package ru.altf000.adapterdelegates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.altf000.adapterdelegates.base.AdapterDelegateItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _headerItems = MutableStateFlow<List<AdapterDelegateItem>>(
        emptyList()
    ).apply {
        val list = mutableListOf<HeaderItem>()
        repeat(3) {
            list.add(HeaderItem("HeaderAdapter $it"))
        }
        value = list
    }
    val headerItems = _headerItems.asStateFlow()

    private val _contentItems = MutableStateFlow<List<AdapterDelegateItem>>(
        emptyList()
    ).apply {
        val list = mutableListOf<ContentItem>()
        repeat(3) {
            list.add(ContentItem("ContentAdapter $it"))
        }
        value = list
    }
    val contentItems = _contentItems.asStateFlow()

    private val _footerItems = MutableStateFlow<List<AdapterDelegateItem>>(
        emptyList()
    ).apply {
        val list = mutableListOf<FooterItem>()
        repeat(3) {
            list.add(FooterItem("FooterAdapter $it"))
        }
        value = list
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
                delay(3000)
                _contentItems.value = _contentItems.value.shuffled()
            }
        }
        viewModelScope.launch {
            while (true) {
                delay(4000)
                _footerItems.value = _footerItems.value.shuffled()
            }
        }
    }
}