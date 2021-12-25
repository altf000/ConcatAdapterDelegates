package ru.altf000.adapterdelegates.adapterdelegates

typealias DItem = AdapterDelegateItem

abstract class AdapterDelegateItem {
    abstract val identifier: Any
    abstract val data: Any?
}