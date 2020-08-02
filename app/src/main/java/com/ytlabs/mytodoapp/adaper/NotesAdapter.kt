package com.ytlabs.mytodoapp.adaper

import android.icu.text.CaseMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.clicklisteners.ItemClickListener
import com.ytlabs.mytodoapp.db.Notes

//import com.ytlabs.mytodoapp.model.Notes

class NotesAdapter(
    private var listNotes: MutableList<Notes>,
    private var itemClickListener: ItemClickListener
) :
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
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(note)
        }

        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                itemClickListener.onUpdate(note)
            }

        })

    }

    // Holds the sub views in the item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val checkBoxMarkStatus: CheckBox = itemView.findViewById(R.id.checkboxMarkStatus)


    }

}