package com.example.tvshows.view.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvshows.data.model.Persons
import com.example.tvshows.data.model.Show
import com.example.tvshows.databinding.ActivityDetailBinding
import com.example.tvshows.view.adapter.TalentAdapter
import com.example.tvshows.view.viewmodel.ShowViewModel
import com.example.tvshows.view.viewmodel.ShowViewModelFactory
import com.squareup.picasso.Picasso


class DetailActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityDetailBinding

    lateinit var viewModelShow: ShowViewModel
    lateinit var viewModelFactory: ShowViewModelFactory

    lateinit var mAdapterResults: TalentAdapter
    lateinit var officialSite: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetailBinding.inflate(LayoutInflater.from(this)).apply { lifecycleOwner = this@DetailActivity }
        setContentView(mBinding.root)

        initObs()
        initView()
        runTvShow(intent.getIntExtra("idShow", 0))
        runTalentsTvShow(intent.getIntExtra("idShow", 0))
    }

    fun initObs(){
        viewModelFactory = ShowViewModelFactory()
        viewModelShow = ViewModelProvider(this, viewModelFactory)
            .get(ShowViewModel::class.java)

        this.lifecycle.addObserver(viewModelShow)

        viewModelShow.mResponse.observe(this, Observer {
            if(it != null){
                mBinding.show = it
                if(it.image != null){
                    Picasso.get().load(it.image.medium).into(mBinding.imageViewDetails)
                    officialSite = it.officialSite.toString()
                }
            }else{
                dialogNoResultsFound()
            }
        })

        viewModelShow.mResponseTalents.observe(this, Observer {
            if(it != null){
                mAdapterResults= TalentAdapter(it)
                mBinding.recyclerViewTalents.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                mBinding.recyclerViewTalents.adapter=mAdapterResults
            }else{
                dialogNoResultsFound()
            }
        })
    }

    fun initView(){
        mBinding.btnVisitSite.setOnClickListener {
            try {
                if(officialSite != null){
                    val browserLPCIntent = Intent(Intent.ACTION_VIEW, Uri.parse(officialSite))
                    startActivity(browserLPCIntent)
                }else{
                    Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun runTvShow(idShow: Int){
        viewModelShow.performLoadShow(idShow)
        viewModelShow.mResponse.value = Show(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
    }

    fun runTalentsTvShow(idShow: Int){
        viewModelShow.performLoadTalentsShow(idShow)
        viewModelShow.mResponseTalents.value = Persons()
    }

    fun dialogNoResultsFound(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("No results found")
        builder.setMessage("Please try again with another query")
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}