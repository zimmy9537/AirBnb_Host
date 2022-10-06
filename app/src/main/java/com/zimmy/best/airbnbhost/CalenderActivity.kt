package com.zimmy.best.airbnbhost

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivityCallenderBinding
import com.zimmy.best.airbnbhost.model.BasicDetails
import com.zimmy.best.airbnbhost.model.DateBnb
import java.text.SimpleDateFormat
import java.util.*


class CalenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCallenderBinding
    private lateinit var photoList: ArrayList<String>
    private lateinit var hostingCode: String

    private lateinit var accountReference: DatabaseReference
    private lateinit var hostingReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference

    private var LOG_TAG = CalenderActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallenderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()


        hostingCode = intent.getStringExtra(Konstants.HOSTING_CODE).toString()
        photoList =
            intent.getStringArrayListExtra(Konstants.DATA4) as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */

//        binding.calView.setOnDateChangeListener(OnDateChangeListener { view, year, month, day ->
//            this.year = year
//            this.month = month
//            this.day = day
//            Log.v("date ", "$day/$month/$year")
//        })


        binding.saveAndNext.setOnClickListener {
            uploadPhotoToFirebase(photoList, hostingCode)
        }
    }

    private fun uploadPhotoToFirebase(photoList: ArrayList<String>, hostingCode: String) {
        binding.progress.visibility = View.VISIBLE
        Toast.makeText(this, "uploading to firebase", Toast.LENGTH_SHORT).show()
        Log.v(LOG_TAG, "list size ${photoList.size}")
        accountReference = FirebaseDatabase.getInstance().reference.child(Konstants.HOSTS)
            .child(mAuth.uid.toString()).child(Konstants.HOSTINGS_MODEL1).child(hostingCode)
            .child(Konstants.BASIC_PHOTO)

        var i = 0
        var j = 0
        while (i < photoList.size) {
            storageReference =
                FirebaseStorage.getInstance().reference.child(mAuth.uid.toString())
                    .child(hostingCode)
                    .child("image_$i")//image no.
            val uri = Uri.parse(photoList[i])
            storageReference.putFile(uri)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        storageReference.downloadUrl.addOnSuccessListener {
                            photoList[j] = it.toString()
                            if (j == 0) {
                                accountReference.setValue(photoList[0])
                            }
                            j++
                            if (j == photoList.size) {
                                hostingReference =
                                    FirebaseDatabase.getInstance().reference.child(Konstants.HOSTINGS_MODEL1)
                                        .child(hostingCode)
                                hostingReference.child(Konstants.HOSTINGDETAILS)
                                    .child(Konstants.PHOTOLIST)
                                    .setValue(photoList)
                                if (!binding.showHosting.isChecked) {
                                    hostingReference.child(Konstants.BASICDETAILS)
                                        .child(Konstants.SHOWHOSTING).setValue(false)
                                }
                                hostingReference.child(Konstants.BASICDETAILS)
                                    .child(Konstants.BASIC_PHOTO)
                                    .setValue(photoList[0])
                                binding.progress.visibility = View.GONE
                                Toast.makeText(this, "successfully uploaded", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@CalenderActivity, HomeActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)

                            }
                        }
                    }
                }
            i++
        }
    }

    private fun randomString(length: Int): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val sb = StringBuilder()
        val random = Random()
        for (i in 0 until length) {
            val index = random.nextInt(alphabet.length)
            val randomChar = alphabet[index]
            sb.append(randomChar)
        }
        val randomString = sb.toString()
        return randomString
    }

//    private fun setDate() {
//        val c: Date = Calendar.getInstance().time
//        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val formattedDate: String = df.format(c)
//        dayOfMonthToday = formattedDate.slice(IntRange(0, 1)).toInt()
//        monthToday = formattedDate.slice(IntRange(3, 4)).toInt()
//        yearToday = formattedDate.slice(IntRange(6, 9)).toInt()
//    }
}