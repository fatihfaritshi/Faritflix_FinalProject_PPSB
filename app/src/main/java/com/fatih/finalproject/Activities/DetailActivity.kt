package com.fatih.finalproject.Activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.widget.NestedScrollView
import com.fatih.finalproject.R
import com.fatih.finalproject.Adapter.ActorListAdapter
import com.fatih.finalproject.Domain.FilmItem
import com.android.volley.RequestQueue
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.fatih.finalproject.Adapter.CategoryEachFilmListAdapter
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var progressBar: ProgressBar
    private lateinit var titleTxt: TextView
    private lateinit var movieRateTxt: TextView
    private lateinit var movieTimeTxt: TextView
    private lateinit var movieSummaryInfo: TextView
    private lateinit var movieActorsInfo: TextView
    private var idFilm: Int = 0
    private lateinit var pic2: ImageView
    private lateinit var backImg: ImageView
    private lateinit var adapterActorList: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var scrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        idFilm = intent.getIntExtra("id", 0)
        initView()
        sendRequest()
    }

    private fun sendRequest(){
        mRequestQueue = Volley.newRequestQueue(this)
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE
        val url = "https://moviesapi.ir/api/v1/movies/$idFilm"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val gson = Gson()
                progressBar.visibility = View.GONE
                scrollView.visibility = View.VISIBLE

                val item = gson.fromJson(response, FilmItem::class.java)

                Glide.with(this)
                    .load(item.poster)
                    .into(pic2)

                titleTxt.text = item.title
                movieRateTxt.text = item.imdbRating
                movieTimeTxt.text = item.runtime
                movieSummaryInfo.text = item.plot
                movieActorsInfo.text = item.actors

                val images = item.images
                if (images != null && images.isNotEmpty()) {
                    adapterActorList = ActorListAdapter(images)
                    recyclerViewActors.adapter = adapterActorList
                }
                val genres = item.genres
                if (genres != null && genres.isNotEmpty()) {
                    adapterCategory = CategoryEachFilmListAdapter(genres)
                    recyclerViewCategory.adapter = adapterCategory
                }
            },
            { error ->
                progressBar.visibility = View.GONE
            }
        )
        mRequestQueue.add(stringRequest)
    }

    private fun initView() {
        titleTxt = findViewById(R.id.movieNameTxt)
        progressBar = findViewById(R.id.progressBarDetail)
        scrollView = findViewById(R.id.scrollView2)
        pic2 = findViewById(R.id.picDetail)
        movieRateTxt = findViewById(R.id.movieStar)
        movieTimeTxt = findViewById(R.id.movieTime)
        movieSummaryInfo = findViewById(R.id.movieSummary)
        movieActorsInfo = findViewById(R.id.movieActorInfo)
        backImg = findViewById(R.id.backImg)
        recyclerViewCategory = findViewById(R.id.genreView)
        recyclerViewActors = findViewById(R.id.imagesRecycler)

        recyclerViewCategory.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewActors.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)

        backImg.setOnClickListener { finish() }
    }
}