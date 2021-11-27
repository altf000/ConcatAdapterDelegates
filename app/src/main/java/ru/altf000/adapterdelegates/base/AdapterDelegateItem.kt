package ru.altf000.adapterdelegates.base

typealias DItem = AdapterDelegateItem

interface AdapterDelegateItem {
    val viewType: Int
    val identifier: Any
    val data: Any
}