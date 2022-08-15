package com.example.alexandrechristie.ui.category

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
import com.example.alexandrechristie.databinding.FragmentCategoryBinding
import com.example.alexandrechristie.ui.home.adapter.WatchViewAdapter
import com.example.alexandrechristie.viewModel.WatchViewModel

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private lateinit var watchViewAdapter: WatchViewAdapter
    private lateinit var watchList: ArrayList<Watch>

    private val viewModel : WatchViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoryBinding.inflate(inflater,container,false)
        val root : View = binding.root
        setUpSearch()
        return root
    }

    private fun setUpSearch() {
        viewModel.watchSearch.observe(viewLifecycleOwner){
            Log.i("food search", "setupSearch: $it")
            getWatch(it)

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchList = arrayListOf()
        initRec()
        getWatch("")
        onClick()
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
        binding.recCategoryList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = watchViewAdapter
        }
    }
    private fun onClick() {
        binding.all.setOnClickListener {
            initRec()
            getWatch("")
        }
        binding.men.setOnClickListener{
            getMenCollection()
        }
        binding.women.setOnClickListener{
            getWomenCollection()
        }
        binding.pair.setOnClickListener{
            getPairCollection()
        }
    }

    private fun getMenCollection() {
        viewModel.getWatchMen().observe(viewLifecycleOwner){
            watchList = it as ArrayList<Watch>
            watchViewAdapter.setData(watchList)
        }
    }
    private fun getWomenCollection() {
        viewModel.getWatchWomen().observe(viewLifecycleOwner){
            watchList = it as ArrayList<Watch>
            watchViewAdapter.setData(watchList)
        }
    }
    private fun getPairCollection() {
        viewModel.getWatchPair().observe(viewLifecycleOwner){
            watchList = it as ArrayList<Watch>
            watchViewAdapter.setData(watchList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}