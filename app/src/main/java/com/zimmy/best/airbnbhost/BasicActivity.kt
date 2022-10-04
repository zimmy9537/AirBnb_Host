package com.zimmy.best.airbnbhost

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivityBasicBinding
import com.zimmy.best.airbnbhost.model.BasicDetails


class BasicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasicBinding
    private var gotLocation = false
    private lateinit var latitude: String
    private lateinit var longitude: String
    private lateinit var locationManager: LocationManager
    private var REQUEST_LOCATION = 999
    private var option = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.gpsLocation.setOnClickListener {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                OnGps()
            } else {
                Toast.makeText(this, "currently bypassing", Toast.LENGTH_SHORT).show()
                latitude="21.683666942812454"
                longitude="70.3761367600009"
                gotLocation = true
//                getLocation()
            }
        }

        binding.next.setOnClickListener {


//            Log.v("price", binding.price.toString())

            val title = binding.title.text.toString()

            Log.v("tittle is ", "$title")

            val primaryAddress = binding.primaryAddress.toString()
//            val price=binding.price.toString()
            val pricing: Double = 20.0

            //vila 0
            //hotel 1
            //flat 2

            option = if (binding.vila.isChecked) {
                0
            } else if (binding.hotel.isChecked) {
                1
            } else if (binding.flat.isChecked) {
                2
            } else {
                Toast.makeText(this@BasicActivity, "Select a type of space", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (title.isEmpty()) {
                Toast.makeText(this@BasicActivity, "Tittle can not be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (primaryAddress.isEmpty()) {
                Toast.makeText(
                    this@BasicActivity,
                    "primary Address can not be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else if (pricing == 0.0) {
                Toast.makeText(this@BasicActivity, "pricing can not be $0", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (!gotLocation) {
                Toast.makeText(this@BasicActivity, "Requires Location access", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val intent = Intent(this@BasicActivity, RoomActivity::class.java)
            val basicData =
                BasicDetails(option, title, primaryAddress, longitude, latitude, pricing,0.0,0,0)
            intent.putExtra(Konstants.DATA, basicData)
            startActivity(intent)
        }
    }

    private fun OnGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val locationRequest = com.google.android.gms.location.LocationRequest()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 3000
            LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(this@BasicActivity)
                            .removeLocationUpdates(this)
                        if (locationResult.locations.size > 0) {
                            val latestLocationIndex: Int = locationResult.locations.size - 1
                            latitude = java.lang.String.valueOf(
                                locationResult.locations[latestLocationIndex].latitude
                            )
                            longitude = java.lang.String.valueOf(
                                locationResult.locations[latestLocationIndex]
                                    .longitude
                            )
                            Toast.makeText(this@BasicActivity, "Located you", Toast.LENGTH_SHORT)
                                .show()
                            gotLocation = true
                        }
                    }
                }, Looper.getMainLooper())
        }
    }
}