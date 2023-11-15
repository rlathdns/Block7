package com.cookandroid.block7

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainPage : BaseActivity() {
    private val mysqlConnection = MysqlConnection()
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {

            val customApplication = application as CustomApplication
            val studentId = customApplication.studentId

            timeRefresh()
            updateUserData(studentId)
            handler.postDelayed(this, 1000) // 1초마다 새로고침
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.main_page)

        val customApplication = application as CustomApplication
        val studentId = customApplication.studentId


        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true

        val userid: TextView = findViewById(R.id.user_id)

        val levelprogress: ProgressBar = findViewById(R.id.level_progress)

        levelprogress.visibility = View.VISIBLE
        userid.setText(studentId)
        levelprogress.max = 100

        val mailboxButton: ImageButton = findViewById(R.id.mailbox_button)

        mailboxButton.setOnClickListener {
            val intent = Intent(this, MailDialog::class.java)
            startActivity(intent)

        }

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val bannerAdapter = BannerAdapter()
        viewPager.adapter = bannerAdapter

        val handler = Handler(Looper.getMainLooper())
        val run = object : Runnable {
            override fun run() {
                var currentPosition = viewPager.currentItem
                val itemCount = bannerAdapter.itemCount
                currentPosition = if (currentPosition == itemCount - 1) 0 else currentPosition + 1
                viewPager.setCurrentItem(currentPosition, true) // true to enable smooth scrolling
                handler.postDelayed(this, 3000) // 3000 milliseconds delay for auto slide
            }
        }
        handler.postDelayed(run, 3000) // Start auto slide with a delay

// Registering the OnPageChangeCallback to reset the auto slider timer when user interacts with ViewPager
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> handler.postDelayed(run, 3000)
                    ViewPager2.SCROLL_STATE_DRAGGING -> handler.removeCallbacks(run)
                    ViewPager2.SCROLL_STATE_SETTLING -> handler.removeCallbacks(run)
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Assuming you have set the adapter to loop, add logic to jump to the start/end accordingly
            }
        })


        val contract_tap_button:ImageButton = findViewById(R.id.contract_tap_button)
        contract_tap_button.setOnClickListener {
            val intent = Intent(this, ContractActivity::class.java)
            startActivity(intent)
        }
    }

    //맨 아래

    override fun onResume() {
        super.onResume()
        handler.post(updateRunnable) // 시작
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable) // 중지
    }

    fun timeRefresh() {
        val currenttime: TextView = findViewById(R.id.current_time)
        val dateFormat = SimpleDateFormat("a hh:mm", Locale.US)
        val currentTimeString = dateFormat.format(Date())
        currenttime.text = currentTimeString
    }

    private fun updateUserData(studentId: String) {
        mysqlConnection.getPlayerByStudentId(studentId) { player ->
            runOnUiThread {
                val username_value = player?.username ?: "No Value found"
                val gem_value = player?.gems
                val gold_value = player?.gold
                val level_value = player?.level
                val current_exp_value = player?.currentExp ?: 0

                val username: TextView = findViewById(R.id.user_name)
                val gem_value_layout: TextView = findViewById(R.id.gem_value)
                val gold_value_layout: TextView = findViewById(R.id.gold_value)
                val level_layout: TextView = findViewById(R.id.level)

                username.text = username_value
                gem_value_layout.text = gem_value.toString()
                gold_value_layout.text = gold_value.toString()
                level_layout.text = "$level_value"

                val levelprogress: ProgressBar = findViewById(R.id.level_progress)
                levelprogress.progress = current_exp_value

                val customApplication = application as CustomApplication
                customApplication.user_name = username_value
            }
        }
    }

}
