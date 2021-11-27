package ru.altf000.adapterdelegates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.altf000.adapterdelegates.base.AdapterDelegateBuilder
import ru.altf000.adapterdepegates.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

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
        binding.recyclerView.adapter = AdapterDelegateBuilder()
            .addAdapter(HeaderAdapterDelegate())
            .addAdapter(ContentAdapterDelegate())
            .addAdapter(FooterAdapterDelegate())
            .addItems(viewModel.headerItems)
            .addItems(viewModel.contentItems)
            .addItems(viewModel.footerItems)
            .build(viewLifecycleOwner)
    }
}