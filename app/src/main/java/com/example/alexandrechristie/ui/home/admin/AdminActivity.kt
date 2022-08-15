package com.example.alexandrechristie.ui.home.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alexandrechristie.R
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.databinding.ActivityAdminBinding
import com.example.alexandrechristie.ui.home.adapter.WatchAdapter
import com.example.alexandrechristie.ui.insertData.WatchActivity
import com.example.alexandrechristie.ui.welcomescreen.WelcomeActivity
import com.example.alexandrechristie.viewModel.WatchViewModel

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val viewModel: WatchViewModel by viewModels()
    private lateinit var watchAdapter: WatchAdapter
    private lateinit var watchList:ArrayList<Watch>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
//            Toast.makeText(context, "add food", Toast.LENGTH_SHORT)

            startActivity(Intent(this@AdminActivity, WatchActivity::class.java))
        }
//
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@AdminActivity, WelcomeActivity::class.java))
        }

        watchList = arrayListOf()
        setUpSearch()
        initRec()
        getWatch("")

    }

    private fun setUpSearch() {
        viewModel.watchSearch.observe(this) {
            Log.i("watch search", "setupSearch: $it")
            getWatch(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getWatch(s: String) {
        viewModel.getWatch(s).observe(this) {
            Log.i("get watch", "setupSearch: $it")
            if (it.isNotEmpty()) {
                watchList = it as ArrayList<Watch>
                watchAdapter.setData(watchList)
            }
        }
    }

    private fun initRec() {
        watchAdapter = WatchAdapter(watchList)
        binding.recWatchList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AdminActivity)
            adapter = watchAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.menu_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search something"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty())
                    viewModel.watchSearch.value = newText.trim()
                else
                    viewModel.watchSearch.value = ""
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}