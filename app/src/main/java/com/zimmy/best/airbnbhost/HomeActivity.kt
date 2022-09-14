package com.zimmy.best.airbnbhost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zimmy.best.airbnbhost.databinding.ActivityHomeBinding
import com.zimmy.best.airbnbhost.databinding.ActivitySignInBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.currentHosting.setOnClickListener {
            startActivity(Intent(this@HomeActivity, CurrentHostingActivity::class.java))
        }

        binding.newHosting.setOnClickListener {
            startActivity(Intent(this@HomeActivity, NewActivity::class.java))
        }
    }
}