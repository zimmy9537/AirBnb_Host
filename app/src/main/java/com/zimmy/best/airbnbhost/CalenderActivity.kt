package com.zimmy.best.airbnbhost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivityCallenderBinding
import com.zimmy.best.airbnbhost.model.BasicDetails

class CalenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCallenderBinding
    private lateinit var basicDetails: BasicDetails
    private lateinit var roomList: ArrayList<String>
    private lateinit var detailMap: HashMap<String, Boolean>
    private lateinit var photoList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallenderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        basicDetails = intent.getSerializableExtra(Konstants.DATA) as BasicDetails
        roomList =
            intent.getStringArrayListExtra(Konstants.DATA2) as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
        detailMap =
            intent.getSerializableExtra(Konstants.DATA3) as HashMap<String, Boolean> /* = java.util.HashMap<kotlin.String, kotlin.Boolean> */
        photoList =
            intent.getStringArrayListExtra(Konstants.DATA4) as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */



    }
}