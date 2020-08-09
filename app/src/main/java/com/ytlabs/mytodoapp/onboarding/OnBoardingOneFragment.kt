package com.ytlabs.mytodoapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ytlabs.mytodoapp.R

// In fragment we don't set the content view as in the activities
class OnBoardingOneFragment : Fragment() {

    private lateinit var textViewNext:TextView
    lateinit var onNextClick:OnNextClick

    // Called when fragment is attached to an activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick = context as OnNextClick
    }

    // Used to inflate the widgets
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }

    // Called immediately after onCreateView()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)
    }

    private fun bindViews(view: View) {
        textViewNext = view.findViewById(R.id.textViewNext)
        clickListeners()
    }

    private fun clickListeners() {
        textViewNext.setOnClickListener {
            onNextClick.onClick()
        }
    }

    interface OnNextClick {
        fun onClick()
    }
}