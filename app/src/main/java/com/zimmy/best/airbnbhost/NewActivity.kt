package com.zimmy.best.airbnbhost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zimmy.best.airbnbhost.databinding.ActivityCurrentHostingBinding
import com.zimmy.best.airbnbhost.databinding.ActivityNewBinding

class NewActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.hotelFlatVila.setOnClickListener {
            startActivity(Intent(this@NewActivity,BasicActivity::class.java))
        }

        binding.getRentedSpaces.setOnClickListener {
            Toast.makeText(this@NewActivity,"Coming soon",Toast.LENGTH_SHORT).show()
        }

        binding.stayWithHost.setOnClickListener {
            Toast.makeText(this@NewActivity,"Coming soon",Toast.LENGTH_SHORT).show()
        }

    }
}