package com.project.ini.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.project.ini.R
import com.project.ini.databinding.ActivitySplashBinding
import com.project.ini.ui.main.BottomMainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, BottomMainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        const val SPLASH_TIME_OUT = 1000L
    }
}