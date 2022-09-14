package com.zimmy.best.airbnbhost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivityFacilityBinding
import com.zimmy.best.airbnbhost.model.BasicDetails

class FacilityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFacilityBinding
    private lateinit var basicData: BasicDetails
    private lateinit var roomList: ArrayList<String>

    private lateinit var detailsMap: HashMap<String, Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacilityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        detailsMap[Konstants.HAIR_DRYER] = false
        detailsMap[Konstants.SHAMPOO] = false
        detailsMap[Konstants.HOT_WATER] = false
        detailsMap[Konstants.ESSENTIALS] = false
        detailsMap[Konstants.HANGERS] = false
        detailsMap[Konstants.COT] = false
        detailsMap[Konstants.AIR_CONDITIONING] = false
        detailsMap[Konstants.HEATER] = false
        detailsMap[Konstants.FIRST_AID] = false
        detailsMap[Konstants.WIFI] = false
        detailsMap[Konstants.DEDICATED_WORKSPACE] = false
        detailsMap[Konstants.REFRIGERATOR] = false
        detailsMap[Konstants.COOKING_BASICS] = false
        detailsMap[Konstants.DISHES_AND_SILVERWARE] = false
        detailsMap[Konstants.PRIVATE_ENTRANCE] = false
        detailsMap[Konstants.GARDEN] = false
        detailsMap[Konstants.FREE_PARKING] = false
        detailsMap[Konstants.PRIVATE_POOL] = false
        detailsMap[Konstants.LONG_TERM_STAYS] = false
        detailsMap[Konstants.KITCHEN] = false
        detailsMap[Konstants.BALCONY] = false


        basicData = intent.getSerializableExtra(Konstants.DATA) as BasicDetails
        roomList = intent.getStringArrayListExtra(Konstants.DATA2) as ArrayList<String>

        if (!roomList.contains(Konstants.BEDROOM_SINGLE) && !roomList.contains(Konstants.BEDROOM_DOUBLE)) {
            binding.bedroomLl.visibility = View.GONE
        } else if (!roomList.contains(Konstants.ATTACHED_BATHROOM)) {
            binding.attachBathroomLl.visibility = View.GONE
        } else if (!roomList.contains(Konstants.KITCHEN)) {
            binding.kitchenLl.visibility = View.GONE
        } else if (!roomList.contains(Konstants.COMMON_SPACE)) {
            binding.outDoorLl.visibility = View.GONE
        }

        binding.saveAndNext.setOnClickListener {
            detailsMap[Konstants.HAIR_DRYER] = binding.hairDryerCb.isChecked
            detailsMap[Konstants.SHAMPOO] = binding.shampooCb.isChecked
            detailsMap[Konstants.HOT_WATER] = binding.hotWaterCb.isChecked
            detailsMap[Konstants.ESSENTIALS] = binding.essentialsCb.isChecked
            detailsMap[Konstants.HANGERS] = binding.hangerCb.isChecked
            detailsMap[Konstants.COT] = binding.cotCb.isChecked
            detailsMap[Konstants.AIR_CONDITIONING] = binding.airConditioningCb.isChecked
            detailsMap[Konstants.HEATER] = binding.heaterCb.isChecked
            detailsMap[Konstants.FIRST_AID] = binding.firstAidCb.isChecked
            detailsMap[Konstants.WIFI] = binding.wifiCb.isChecked
            detailsMap[Konstants.DEDICATED_WORKSPACE] = binding.dedicatedWorkspaceCb.isChecked
            detailsMap[Konstants.KITCHEN] = binding.kitchenCb.isChecked
            detailsMap[Konstants.REFRIGERATOR] = binding.refrigeratorCb.isChecked
            detailsMap[Konstants.COOKING_BASICS] = binding.cookingBasicsCb.isChecked
            detailsMap[Konstants.DISHES_AND_SILVERWARE] = binding.dishesSilverWareLl.isChecked
            detailsMap[Konstants.PRIVATE_ENTRANCE] = binding.privateEntrance.isChecked
            detailsMap[Konstants.BALCONY] = binding.balconyCb.isChecked
            detailsMap[Konstants.GARDEN] = binding.gardenCb.isChecked
            detailsMap[Konstants.FREE_PARKING]=binding.freeParkingCb.isChecked
            detailsMap[Konstants.PRIVATE_POOL]=binding.poolCb.isChecked
            detailsMap[Konstants.LONG_TERM_STAYS]=binding.longTermsLl.isChecked

            val intent=Intent(this@FacilityActivity,PhotoActivity::class.java)
            intent.putExtra(Konstants.DATA,basicData)
            intent.putExtra(Konstants.DATA2,roomList)
            intent.putExtra(Konstants.DATA3,detailsMap)
            startActivity(intent)
        }

    }
}