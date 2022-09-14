package com.zimmy.best.airbnbhost

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivityRoomBinding
import com.zimmy.best.airbnbhost.model.BasicDetails

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var basicData: BasicDetails
    private lateinit var roomList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        roomList = ArrayList()
        basicData = intent.getSerializableExtra(Konstants.DATA) as BasicDetails

        binding.bedSingle.setOnClickListener {
            roomList.add(Konstants.BEDROOM_SINGLE)
            fillTheLinearLayout(Konstants.BEDROOM_SINGLE)
        }

        binding.bedDouble.setOnClickListener {
            roomList.add(Konstants.BEDROOM_DOUBLE)
            fillTheLinearLayout(Konstants.BEDROOM_DOUBLE)
        }

        binding.attachBathroom.setOnClickListener {
            roomList.add(Konstants.ATTACHED_BATHROOM)
            fillTheLinearLayout(Konstants.ATTACHED_BATHROOM)
        }

        binding.balcony.setOnClickListener {
            roomList.add(Konstants.BALCONY)
            fillTheLinearLayout(Konstants.BALCONY)
        }

        binding.kitchen.setOnClickListener {
            roomList.add(Konstants.KITCHEN)
            fillTheLinearLayout(Konstants.KITCHEN)
        }

        binding.commonSpace.setOnClickListener {
            roomList.add(Konstants.COMMON_SPACE)
            fillTheLinearLayout(Konstants.COMMON_SPACE)
        }

        binding.next.setOnClickListener {
            val intent=Intent(this@RoomActivity,FacilityActivity::class.java)
            intent.putExtra(Konstants.DATA,basicData)
            intent.putExtra(Konstants.DATA2,roomList)
            startActivity(intent)
        }

    }

    fun fillTheLinearLayout(room: String) {
        val view = LayoutInflater.from(this@RoomActivity)
            .inflate(R.layout.room_view, null, false)
        val resultTextView = view.findViewById<TextView>(R.id.roomTextView)
        resultTextView.text = room
        binding.roomsLl.addView(view)
    }
}