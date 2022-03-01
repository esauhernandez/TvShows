package com.example.tvshows.view.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvshows.data.model.QuerySearch
import com.example.tvshows.data.model.TvPrograms
import com.example.tvshows.databinding.ActivityMainBinding
import com.example.tvshows.domain.usecase.UseCaseRoomSearch
import com.example.tvshows.view.adapter.OnQueryClickListener
import com.example.tvshows.view.adapter.OnShowClickListener
import com.example.tvshows.view.adapter.ProductAdapter
import com.example.tvshows.view.adapter.QueryAdapter
import com.example.tvshows.view.viewmodel.RoomViewModel
import com.example.tvshows.view.viewmodel.SearchViewModel
import com.example.tvshows.view.viewmodel.SearchViewModelFactory
import java.util.*


class MainActivity : AppCompatActivity(), OnQueryClickListener, OnShowClickListener {

    lateinit var mBinding: ActivityMainBinding

    lateinit var viewModelSearch: SearchViewModel
    lateinit var viewModelFactory: SearchViewModelFactory
    private val mViewModelRoom by lazy { getViewModelRoom() }

    lateinit var mAdapterResults: ProductAdapter
    lateinit var mAdapterQueries: QueryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this)).apply { lifecycleOwner = this@MainActivity }
        setContentView(mBinding.root)

        initObs()
        initViews()
        runTvPrograms()

    }

    override fun onResume() {
        super.onResume()
        mViewModelRoom.getAllQueriesInDB()
    }

    override fun onBackPressed() {
        if(mBinding.recyclerViewQueries.visibility == View.VISIBLE){
            mBinding.recyclerViewQueries.visibility = View.GONE
        }else{
            super.onBackPressed()
        }
    }

    fun initObs(){
        viewModelFactory = SearchViewModelFactory()
        viewModelSearch = ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)

        this.lifecycle.addObserver(viewModelSearch)
        this.lifecycle.addObserver(mViewModelRoom)

        viewModelSearch.mResponse.observe(this, Observer {
            if(it != null){
                mAdapterResults= ProductAdapter(it, this)
                mBinding.recyclerViewResults.layoutManager = LinearLayoutManager(this)
                mBinding.recyclerViewResults.adapter=mAdapterResults
            }else{
                dialogNoResultsFound()
            }
            enableViews()
        })
    }

    fun initViews(){
        mBinding.button.setOnClickListener {
            if(mBinding.searchView.editText?.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Enter query to search", Toast.LENGTH_SHORT).show()
            }else{
                var querySearch = QuerySearch(UUID.randomUUID().toString(), mBinding.searchView.editText?.text.toString(), 0L)
                runSearch(querySearch)
            }
        }

        mBinding.searchView.editText?.setOnFocusChangeListener { v, hasFocus ->
            if(v.hasFocus()){
                mViewModelRoom.getAllQueriesInDB()
                mViewModelRoom.listQueries.observe(this, Observer {queries ->
                    if(queries.isNotEmpty()){
                        mBinding.recyclerViewQueries.visibility = View.VISIBLE
                        mAdapterQueries = QueryAdapter(queries.reversed(), this)
                        mBinding.recyclerViewQueries.layoutManager = LinearLayoutManager(this)
                        mBinding.recyclerViewQueries.adapter=mAdapterQueries
                    }
                })
            }
        }

        mBinding.searchView.editText?.setOnClickListener {
            if(mBinding.searchView.editText!!.hasFocus() && mBinding.recyclerViewQueries.visibility == View.GONE){
                mViewModelRoom.getAllQueriesInDB()
                mViewModelRoom.listQueries.observe(this, Observer {queries ->
                    if(queries.isNotEmpty()){
                        mBinding.recyclerViewQueries.visibility = View.VISIBLE
                        mAdapterQueries = QueryAdapter(queries.reversed(), this)
                        mBinding.recyclerViewQueries.layoutManager = LinearLayoutManager(this)
                        mBinding.recyclerViewQueries.adapter=mAdapterQueries
                    }
                })
            }
        }
    }

    fun runTvPrograms(){
        disableViews()
        mBinding.recyclerViewQueries.visibility = View.GONE
        viewModelSearch.performLoadTvPrograms()
        viewModelSearch.mResponse.value = TvPrograms()
    }

    fun runSearch(query: QuerySearch){
        disableViews()
        mBinding.recyclerViewQueries.visibility = View.GONE
        UseCaseRoomSearch.saveQueryRoom(query, application)
        viewModelSearch.performSearch(query.title.trim())
        viewModelSearch.mResponse.value = TvPrograms()
    }

    fun disableViews(){
        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.searchView.clearFocus()
        mBinding.searchView.isEnabled = false
        mBinding.button.isEnabled = false
    }

    fun enableViews(){
        mBinding.progressBar.visibility = View.GONE
        mBinding.searchView.editText?.setText("")
        mBinding.searchView.isEnabled = true
        mBinding.button.isEnabled = true
    }

    fun dialogNoResultsFound(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("No results found")
        builder.setMessage("Please try again with another query")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
            enableViews()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun getViewModelRoom(): RoomViewModel {
        val factory = RoomViewModel.Factory(application)
        return ViewModelProvider(this, factory).get(RoomViewModel::class.java)
    }

    override fun onQueryClick(view: View, query: QuerySearch) {
        runSearch(query)
        mBinding.searchView.editText?.setText(query.title)
    }

    override fun onShowClick(view: View, idShow: Int) {
        var intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("idShow", idShow)
        startActivity(intent)
    }
}
