package com.ytlabs.mytodoapp.adaper

import android.icu.text.CaseMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.model.Notes

class NotesAdapter(var listNotes: MutableList<Notes>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    // Inflates and creates the sub layout/view of the item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    // Get the total no. of items
    override fun getItemCount(): Int {
        return listNotes.size
    }

    // Set/bind the data and event listeners for sub views in the item
    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val note = listNotes[position]

        holder.textViewTitle.text = note.title
        holder.textViewDescription.text = note.description

    }

    // Holds the sub views in the item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        var textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)

    }

}