package com.zimmy.best.airbnbhost.adapter

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.zimmy.best.airbnbhost.BookingRequestActivity
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.AcceptBookingBinding
import com.zimmy.best.airbnbhost.databinding.BookingRequestItemBinding
import com.zimmy.best.airbnbhost.model.BookingDetails

class BookingRequestAdapter(private val guestList: ArrayList<BookingDetails>, val context: Context) :
    RecyclerView.Adapter<BookingRequestAdapter.BookingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = BookingRequestItemBinding.inflate(LayoutInflater.from(context))
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        with(holder) {
            with(guestList[position]) {
                var count = 0
                var basicPhoto: String
                val hostReference =
                    FirebaseDatabase.getInstance().reference.child(Konstants.HOSTS)
                        .child(FirebaseAuth.getInstance().uid.toString())
                hostReference.child(Konstants.HOSTINGS_MODEL1).child(this.hosting_id).child(Konstants.BASIC_PHOTO)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            basicPhoto = snapshot.getValue(String::class.java).toString()
                            Log.d("photo"," url-> $basicPhoto")
                            Picasso.get().load(basicPhoto).into(binding.basicImage)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d(
                                BookingRequestActivity::class.java.simpleName,
                                "database error occurred ${error.message}"
                            )
                        }
                    })
                val adults = guest?.adult
                val children = guest?.children
                if (adults != null) {
                    count += adults
                }
                if (children != null) {
                    count += children
                }
                binding.name.text = user_name
                binding.details.text = hostingDetail
                binding.phoneNumber.text = user_phone
                binding.guest.text = "$count Persons"

                binding.root.setOnClickListener {
                    class AcceptDialog(context: Context) : Dialog(context) {
                        lateinit var binding: AcceptBookingBinding
                        override fun onCreate(savedInstanceState: Bundle?) {
                            super.onCreate(savedInstanceState)
                            binding = AcceptBookingBinding.inflate(layoutInflater)
                            setContentView(binding.root)
                            binding.accept.setOnClickListener {
                                Toast.makeText(
                                    context,
                                    "Requesting User to Pay",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //remove the request booking state
                                hostReference.child(Konstants.BOOKINGREQUEST).child(booking_id)
                                    .removeValue()
                                hostReference.child(Konstants.PAYMENTLEFT).child(hosting_id)
                                    .setValue(this@with)
                                //notify user to pay
                                Log.v("Booking successful ",hosting_id)
                                val userReference = FirebaseDatabase.getInstance().reference
                                userReference.child(Konstants.USERS).child(user_uid).child(Konstants.PAYMENTLEFT)
                                    .child(hosting_id).setValue(this@with)
                                this.dismiss()
                            }

                        }
                    }
                    val dialog=AcceptDialog(context)
                    dialog.show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return guestList.size
    }

    class BookingViewHolder(val binding: BookingRequestItemBinding) : ViewHolder(binding.root)
}