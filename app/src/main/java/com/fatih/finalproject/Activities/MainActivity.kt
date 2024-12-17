package com.fatih.finalproject.Activities

import android.util.Log
import com.google.gson.Gson
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.CompositePageTransformer
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.reflect.TypeToken
import com.fatih.finalproject.Adapter.CategoryListAdapter
import com.fatih.finalproject.Adapter.SliderAdapters
import com.fatih.finalproject.Adapter.FilmListAdapter
import com.fatih.finalproject.Domain.GenresItem
import com.fatih.finalproject.Domain.SliderItems
import com.fatih.finalproject.Domain.ListFilm
import com.fatih.finalproject.R

class MainActivity : AppCompatActivity() {
    private lateinit var adapterBestMovies: RecyclerView.Adapter<*>
    private lateinit var adapterUpComming: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewBestMovies: RecyclerView
    private lateinit var recyclerViewUpcomming: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView

    private var viewPager2: ViewPager2? = null
    private val slideHandler: Handler = Handler(Looper.getMainLooper()) // Make sure it's on the main thread

    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var mStringRequest2: StringRequest
    private lateinit var mStringRequest3: StringRequest

    private lateinit var loading1: ProgressBar
    private lateinit var loading2: ProgressBar
    private lateinit var loading3: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        banners()
        sendRequestBestMovies()
        sendRequestCategory()
        sendRequestUpComming()
    }

    private fun sendRequestBestMovies() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading1.visibility = View.VISIBLE
        mStringRequest = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1",
            { response ->
                val gson = Gson()
                loading1.visibility = View.GONE
                val items = gson.fromJson(response, ListFilm::class.java)
                adapterBestMovies = FilmListAdapter(items)
                recyclerViewBestMovies.adapter = adapterBestMovies
            },
            { error ->
                loading1.visibility = View.GONE
                Log.i("UILover", "onErrorResponse: ${error.toString()}")
            }
        )
        mRequestQueue.add(mStringRequest)
    }

    private fun sendRequestCategory() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading2.visibility = View.VISIBLE
        mStringRequest2 = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres",
            { response ->
                val gson = Gson()
                loading2.visibility = View.GONE
                val catList: ArrayList<GenresItem> = gson.fromJson(response, object : TypeToken<ArrayList<GenresItem>>() {}.type)
                adapterCategory = CategoryListAdapter(catList)
                recyclerViewCategory.adapter = adapterCategory
            },
            { error ->
                loading2.visibility = View.GONE
                Log.i("UILover", "onErrorResponse: ${error.toString()}")
            }
        )
        mRequestQueue.add(mStringRequest2)
    }

    private fun sendRequestUpComming() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading3.visibility = View.VISIBLE
        mStringRequest3 = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2",
            { response ->
                val gson = Gson()
                loading3.visibility = View.GONE
                val items = gson.fromJson(response, ListFilm::class.java)
                adapterUpComming = FilmListAdapter(items)
                recyclerViewUpcomming.adapter = adapterUpComming
            },
            { error ->
                loading3.visibility = View.GONE
                Log.i("UILover", "onErrorResponse: ${error.toString()}")
            }
        )
        mRequestQueue.add(mStringRequest3)
    }

    private fun banners() {
        val sliderItems: MutableList<SliderItems> = ArrayList()
        sliderItems.add(SliderItems(R.drawable.wide))
        sliderItems.add(SliderItems(R.drawable.wide1))
        sliderItems.add(SliderItems(R.drawable.wide3))

        viewPager2?.apply {
            adapter = SliderAdapters(sliderItems, this) // Safe call and using apply to access the view
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER)

            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(50))
                addTransformer { page, position ->
                    val r = 1 - Math.abs(position)
                    page.scaleY = 1.0f * r * 1.0f
                }
            }

            setPageTransformer(compositePageTransformer)
            setCurrentItem(1)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    slideHandler.removeCallbacks(slideRunnable)
                }
            })
        }
    }

    // Runnable in Kotlin
    private val slideRunnable = Runnable {
        viewPager2?.apply {
            setCurrentItem(currentItem + 1, true) // Move to the next item
        }
    }

    override fun onPause() {
        super.onPause()
        slideHandler.removeCallbacks(slideRunnable)
    }

    override fun onResume() {
        super.onResume()
        slideHandler.postDelayed(slideRunnable, 2000)
    }

    private fun initView() {
        viewPager2 = findViewById(R.id.viewpagerSlider)
        recyclerViewBestMovies = findViewById(R.id.view1)
        recyclerViewBestMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewUpcomming = findViewById(R.id.view3)
        recyclerViewUpcomming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewCategory = findViewById(R.id.view2)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        loading1 = findViewById(R.id.progressBar1)
        loading2 = findViewById(R.id.progressBar2)
        loading3 = findViewById(R.id.progressBar3)

    }
}
