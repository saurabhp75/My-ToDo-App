package com.ytlabs.mytodoapp.clicklisteners

import com.ytlabs.mytodoapp.db.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}