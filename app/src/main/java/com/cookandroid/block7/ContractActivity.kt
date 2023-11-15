package com.cookandroid.block7

import DatabaseHelper
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import kotlin.random.Random

class ContractActivity : BaseActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contract_activity)

        db = DatabaseHelper(this)

        val customApplication = application as CustomApplication
        val studentId = customApplication.studentId

        val contractTenTimeButton: ImageButton = findViewById(R.id.contract_10time)
        val contractOneTimeButton: ImageButton = findViewById(R.id.contract_1time)

        contractOneTimeButton.setOnClickListener {
            var try_count= db.getContractTryCount(studentId) //시도 횟수를 불러오기
            drawCharacterAndUpdate(studentId, try_count) //학번과 시도횟수 바탕으로 뽑기+업데이트
            db.updateContractTryCount(studentId) //시도횟수 업데이트
        }

        contractTenTimeButton.setOnClickListener {
            for (i in 1..10) {
                var try_count= db.getContractTryCount(studentId)
                drawCharacterAndUpdate(studentId, try_count)
                db.updateContractTryCount(studentId)
            }
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

    fun drawCharacterAndUpdate(studentId: String, pullsSoFar: Int) {
            if (pullsSoFar == 0) {
                handleDrawResultForStar(5, true, studentId)
                return
            }

            if(pullsSoFar != 0 && pullsSoFar % 10 == 0) {
                val randomfor4star = Random.nextDouble(100.0)
                when {
                    randomfor4star <= 98.4 -> {
                        val isPickup = Random.nextDouble(100.0) <= 50.0
                        handleDrawResultForStar(4, isPickup, studentId)
                    }

                    else -> {
                        val isPickup = Random.nextDouble(100.0) <= 50.0
                        handleDrawResultForStar(5, isPickup, studentId)
                    }
                }
                return
            }

        val random = Random.nextDouble(100.0)
        when {
            random <= 88.4 -> {
                val enhanceAmount = when (Random.nextDouble(100.0)) {
                    in 0.0..50.0 -> 3
                    in 50.0..80.0 -> 5
                    else -> 7
                }
                db.addExpBook(studentId, enhanceAmount)
                db.pullRecord(studentId, "expbook", enhanceAmount)
            }
            random <= 98.4 -> {
                val isPickup = Random.nextDouble(100.0) <= 50.0
                handleDrawResultForStar(4, isPickup, studentId)
            }
            else -> {
                val isPickup = Random.nextDouble(100.0) <= 50.0
                handleDrawResultForStar(5, isPickup, studentId)
            }
        }
    }

    fun handleDrawResultForStar(star: Int, isPickup: Boolean, studentId: String) {
        val characters = if (isPickup) {
            db.getPickupCharacters().filter { it.grade == star }
        } else {
            db.getNonPickupCharacters(star)
        }

        if (characters.isNotEmpty()) {
            val character = characters[Random.nextInt(characters.size)]
            db.updateCharacterOwnershipOrBreakthrough(character.name)

            Toast.makeText(this, character.name, Toast.LENGTH_SHORT).show()
            db.pullRecord(studentId,  character.name, 1)
        } else {
        }
    }
}

