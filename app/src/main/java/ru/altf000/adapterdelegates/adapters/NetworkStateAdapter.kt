package ru.altf000.adapterdelegates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.altf000.adapterdepegates.databinding.ItemNetworkStateBinding

class NetworkStateAdapter(
    private val adapter: PagingDataAdapter<*, *>
) : LoadStateAdapter<NetworkStateAdapter.NetworkStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = NetworkStateViewHolder(
        ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) { adapter.retry() }

    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class NetworkStateViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryAction: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryAction() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                val isError = loadState is LoadState.Error
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = isError
                errorMessage.isVisible = isError
            }
        }
    }
}
