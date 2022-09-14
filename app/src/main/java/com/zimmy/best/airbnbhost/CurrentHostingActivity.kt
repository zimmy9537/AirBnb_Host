package com.zimmy.best.airbnbhost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zimmy.best.airbnbhost.databinding.ActivityCurrentHostingBinding
import com.zimmy.best.airbnbhost.databinding.ActivityHomeBinding

class CurrentHostingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentHostingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentHostingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}