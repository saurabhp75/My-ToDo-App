package com.ytlabs.mytodoapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.adaper.BlogsAdapter
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.ParsedRequestListener
import com.ytlabs.mytodoapp.model.JsonResponse


class BlogActivity : AppCompatActivity() {
    private val TAG = "BlogActivity"
    private lateinit var recyclerviewBlogs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(JsonResponse::class.java, object : ParsedRequestListener<JsonResponse> {
                override fun onResponse(response: JsonResponse?) {
                    setupRecyclerView(response)
                }

                override fun onError(anError: ANError?) {
                    anError?.localizedMessage?.let { Log.d(TAG, it) }
                }
            })
    }

    private fun bindViews() {
        recyclerviewBlogs = findViewById(R.id.recyclerViewBlogs)
    }

    private fun setupRecyclerView(response: JsonResponse?) {
        val blogAdapter = response?.data?.let { BlogsAdapter(it) }
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerviewBlogs.layoutManager = linearLayoutManager
        recyclerviewBlogs.adapter = blogAdapter
    }
}