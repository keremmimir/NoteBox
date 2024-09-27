package com.example.notebox.ui.onboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.notebox.adapter.OnboardingAdapter
import com.example.notebox.databinding.ActivityOnBoardingBinding
import com.example.notebox.ui.MainActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = OnboardingAdapter(this)
        binding.viewPager.adapter = adapter

        binding.buttonNext.setOnClickListener {
            if (binding.viewPager.currentItem < adapter.itemCount - 1) {
                binding.viewPager.currentItem += 1
            } else {
                val sharedPreferences = getSharedPreferences("onboarding", Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("onboarding_completed", true).apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.buttonBack.setOnClickListener {
            if (binding.viewPager.currentItem > 0) {
                binding.viewPager.currentItem -= 1
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.buttonBack.visibility = if (position == 0) View.GONE else View.VISIBLE

                binding.buttonNext.text = if (position == adapter.itemCount - 1) "BİTTİ" else "İLERİ"
            }
        })
    }
}