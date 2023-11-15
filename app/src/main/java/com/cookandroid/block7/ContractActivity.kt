package com.cookandroid.block7

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import kotlin.random.Random

class ContractActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contract_activity)

        val customApplication = application as CustomApplication
        val studentId = customApplication.studentId

        val contractTenTimeButton: ImageButton = findViewById(R.id.contract_10time)
        val contractOneTimeButton: ImageButton = findViewById(R.id.contract_1time)

        contractOneTimeButton.setOnClickListener {
        }

        contractTenTimeButton.setOnClickListener {
        }

        val close_button:ImageButton = findViewById(R.id.close_button)
        close_button.setOnClickListener{
            finish()
        }

        val viewPager: ViewPager2 = findViewById(R.id.banner)
        val banneradapter = ContractBannerAdapter()
        viewPager.adapter = banneradapter

        val startPage = Integer.MAX_VALUE / 2
        val startPosition = startPage - (startPage % banneradapter.banners.size)
        viewPager.setCurrentItem(startPosition, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val lastPage = banneradapter.itemCount - 1
                if (position == 0) {
                    viewPager.setCurrentItem(lastPage - 1, false)
                } else if (position == lastPage) {
                    viewPager.setCurrentItem(1, false)
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
    }
}

