package com.ytlabs.mytodoapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ytlabs.mytodoapp.R


class OnBoardingTwoFragment : Fragment() {

    private lateinit var textViewDone: TextView
    private lateinit var textViewBack: TextView
    lateinit var onOptionClick: OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        textViewDone = view.findViewById(R.id.textViewDone)
        textViewBack = view.findViewById(R.id.textViewBack)
        clickListener()
    }

    private fun clickListener() {
        textViewBack.setOnClickListener {
            onOptionClick.onOptionBack()
        }

        textViewDone.setOnClickListener {
            onOptionClick.onOptionDone()
        }
    }

    interface OnOptionClick {
        fun onOptionBack()
        fun onOptionDone()
    }
}