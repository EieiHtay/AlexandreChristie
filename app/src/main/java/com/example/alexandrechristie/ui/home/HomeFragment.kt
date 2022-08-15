package com.example.alexandrechristie.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.databinding.FragmentHomeBinding
import com.example.alexandrechristie.ui.home.adapter.WatchViewAdapter
import com.example.alexandrechristie.viewModel.WatchViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var watchViewAdapter: WatchViewAdapter
    private lateinit var watchList: ArrayList<Watch>

    private val viewModel : WatchViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root : View = binding.root

        setUpSearch()
        return root
    }

    private fun setUpSearch() {
        viewModel.watchSearch.observe(viewLifecycleOwner){
            Log.i("watch search", "setupSearch: $it")
            getWatch(it)
            getLatestCollection()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchList = arrayListOf()
        initRec()
//        getWatch("")
//        onClick()
        getLatestCollection()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getWatch(s: String) {
        viewModel.getWatch(s).observe(viewLifecycleOwner){
            Log.i("get food", "setupSearch: $it")
            if(it.isNotEmpty()) {
                watchList = it as ArrayList<Watch>
                watchViewAdapter.setData(watchList)
            }
        }
    }

    private fun initRec() {
        watchViewAdapter = WatchViewAdapter(watchList)
        binding.recWatchList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = watchViewAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun getLatestCollection() {
        viewModel.getLatestWatch().observe(viewLifecycleOwner){
            watchList = it as ArrayList<Watch>
            watchViewAdapter.setData(watchList)
        }
    }
}