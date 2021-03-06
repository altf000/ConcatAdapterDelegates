package ru.altf000.adapterdelegates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.altf000.adapterdelegates.adapterdelegates.CompositePagingAdapter
import ru.altf000.adapterdelegates.adapterdelegates.createAdapter
import ru.altf000.adapterdelegates.adapterdelegates.delegateSelector
import ru.altf000.adapterdelegates.adapters.FooterAdapterDelegate
import ru.altf000.adapterdelegates.adapters.HeaderAdapterDelegate
import ru.altf000.adapterdelegates.adapters.content.ContentAdapterDelegateSelector
import ru.altf000.adapterdelegates.utils.collectLatest
import ru.altf000.adapterdepegates.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var pagingAdapter: CompositePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = createAdapter(
            viewLifecycleOwner,
            binding.recyclerView,
            delegateSelector {
                addDelegate(HeaderAdapterDelegate())
                addDelegateSelector(ContentAdapterDelegateSelector())
                addDelegate(FooterAdapterDelegate())
            }
        ) {
            addAdapters(viewModel.list1, viewModel.list2, viewModel.list3)
            pagingAdapter = addPagingAdapter(viewModel.pager).withNetworkStateAdapter()
        }
        pagingAdapter.loadingState.collectLatest(viewLifecycleOwner) { isLoading ->
            binding.root.isRefreshing = isLoading
        }
        binding.root.setOnRefreshListener {
            pagingAdapter.refresh()
        }
    }
}