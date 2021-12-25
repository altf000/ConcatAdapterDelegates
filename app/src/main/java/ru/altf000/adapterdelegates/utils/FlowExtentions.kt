package ru.altf000.adapterdelegates.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectLatest(
    viewLifecycleOwner: LifecycleOwner,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    cancelBlock: (() -> Unit)? = null,
    block: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            try {
                flowOn(dispatcher).collectLatest { block(it) }
            } finally {
                if (!isActive) {
                    cancelBlock?.invoke()
                }
            }
        }
    }
}