package ru.altf000.adapterdelegates.base

typealias DItem = AdapterDelegateItem

abstract class AdapterDelegateItem {
    abstract val identifier: Any
    abstract val data: Any
}