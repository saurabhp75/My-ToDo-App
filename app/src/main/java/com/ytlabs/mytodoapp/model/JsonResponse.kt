package com.ytlabs.mytodoapp.model

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ytlabs.mytodoapp.R

data class JsonResponse(val statusCode: String, val message: String, val data: List<Data>) {

    // Holds the sub views in the item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val checkBoxMarkStatus: CheckBox = itemView.findViewById(R.id.checkboxMarkStatus)
        val imageView: ImageView = itemView.findViewById(R.id.imageViewNotes)
    }
}

data class Data(
    val title: String,
    val description: String,
    val author: String,
    val img_url: String,
    val blog_url: String,
    val published_at: String
)