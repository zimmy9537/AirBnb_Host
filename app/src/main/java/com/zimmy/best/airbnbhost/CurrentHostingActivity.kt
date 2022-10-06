package com.zimmy.best.airbnbhost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivityCurrentHostingBinding
import com.zimmy.best.airbnbhost.databinding.ActivityHomeBinding
import com.zimmy.best.airbnbhost.model.BasicDetails

class CurrentHostingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentHostingBinding

    private lateinit var hostingReference: DatabaseReference
    private lateinit var listedList: ArrayList<BasicDetails>
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentHostingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        checkForData()

    }

    fun checkForData() {
        mAuth = FirebaseAuth.getInstance()
        hostingReference = FirebaseDatabase.getInstance().reference.child(Konstants.HOSTS)
            .child(mAuth.uid.toString()).child(Konstants.HOSTINGS_MODEL1)

    }
}