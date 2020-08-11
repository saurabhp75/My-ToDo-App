package com.ytlabs.mytodoapp.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.utils.PrefConstant
import com.ytlabs.mytodoapp.utils.StoreSession
import com.ytlabs.mytodoapp.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick,
    OnBoardingTwoFragment.OnOptionClick {
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        bindView()
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        // 2nd fragment
        StoreSession.write(PrefConstant.ON_BOARDED_SUCCESSFULLY, true)

        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}

// First time
// splash -> onBoarding -> Login -> MyNotes

// 2nd time onwards
// Splash -> Login -> MyNotes
// Splash -> MyNotes