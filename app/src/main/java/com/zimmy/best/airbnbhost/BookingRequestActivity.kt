package com.zimmy.best.airbnbhost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.adapter.BookingRequestAdapter
import com.zimmy.best.airbnbhost.databinding.ActivityBookingRequestBinding
import com.zimmy.best.airbnbhost.model.BookingDetails
import kotlin.math.log

class BookingRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingRequestBinding
    private lateinit var bookingReference: DatabaseReference
    private lateinit var guestList: ArrayList<BookingDetails>
    private var LOG_TAG = BookingRequestActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        guestList = ArrayList()
        binding.progress.visibility = View.VISIBLE
        binding.guestView.layoutManager = LinearLayoutManager(this)
        binding.guestView.adapter =
            BookingRequestAdapter(guestList, this@BookingRequestActivity)

        bookingReference = FirebaseDatabase.getInstance().reference.child(Konstants.HOSTS)
        bookingReference.child(FirebaseAuth.getInstance().uid.toString())
            .child(Konstants.BOOKINGREQUEST)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (bookingCode in snapshot.children) {
                            Log.d(LOG_TAG, "BOOKING CODE ${bookingCode.key}")
                            val bookingDetails =
                                bookingCode.getValue(BookingDetails::class.java)
                            if (bookingDetails != null) {
                                guestList.add(bookingDetails)
                                (binding.guestView.adapter as BookingRequestAdapter).notifyDataSetChanged()
                                Log.d(LOG_TAG, "BOOKING DETAILS $bookingDetails")
                            }
                        }
                        binding.progress.visibility = View.GONE
                    } else {
                        Log.d(LOG_TAG, "snapshot does not exist")
                        binding.progress.visibility = View.GONE
                        binding.noBookingsTv.visibility = View.VISIBLE
                        guestList.clear()
                        (binding.guestView.adapter as BookingRequestAdapter).notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(
                        BookingRequestActivity::class.java.simpleName,
                        "database error occurred ${error.message}"
                    )
                }
            })
    }
}