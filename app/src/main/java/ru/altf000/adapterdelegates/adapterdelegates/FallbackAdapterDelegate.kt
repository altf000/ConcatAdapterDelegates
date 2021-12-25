package ru.altf000.adapterdelegates.adapterdelegates

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.random.Random

class FallbackAdapterDelegate : AdapterDelegate<DItem, ViewBinding>() {

    override val viewType = Integer.MAX_VALUE
    override val itemClass = FallbackAdapterDelegateItem::class.java
    override fun createBinding(parent: ViewGroup) = ViewBinding { View(parent.context) }
    override fun onBind(item: DItem, binding: ViewBinding, position: Int, payloads: List<Any>) {}
    override fun onUnbind(binding: ViewBinding) {}

    data class FallbackAdapterDelegateItem(val item: Any?) : DItem() {
        override val identifier = Random.nextLong()
        override val data: Any? = null
    }
}
