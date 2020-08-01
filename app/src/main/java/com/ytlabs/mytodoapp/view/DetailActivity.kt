package com.ytlabs.mytodoapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ytlabs.mytodoapp.utils.AppConstant
import com.ytlabs.mytodoapp.R

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"
    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setupIntentData()
    }

    private fun setupIntentData() {
        val title = intent.getStringExtra(AppConstant.TITLE)
        val description = intent.getStringExtra(AppConstant.DESCRIPTION)
        textViewTitle.text = title
        textViewDescription.text = description
//        Log.d(TAG, "$title: $description");

    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)

    }
}