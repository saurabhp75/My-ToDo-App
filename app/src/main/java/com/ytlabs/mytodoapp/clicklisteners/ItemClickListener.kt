package com.ytlabs.mytodoapp.clicklisteners

import com.ytlabs.mytodoapp.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
}