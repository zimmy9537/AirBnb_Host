package com.zimmy.best.airbnbhost

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.zimmy.best.airbnbhost.Konstants.Konstants
import com.zimmy.best.airbnbhost.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    val GOOGLE_SIGN_IN = 64
    val TAG = SignInActivity::class.simpleName
    lateinit var mAuth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var accountReference: DatabaseReference
    lateinit var generalReference: DatabaseReference
    lateinit var personalPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private var WEB_CLIENT_ID =
        "420136098766-sa6jknqv7js9iqe5c3hvbmc6mibj3a2l.apps.googleusercontent.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        personalPreference = getSharedPreferences(Konstants.PERSONAL, Context.MODE_PRIVATE)
        editor = personalPreference.edit()

        mAuth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail().build()
        gsc = GoogleSignIn.getClient(this@SignInActivity, gso)


        binding.signInBt.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val intent = gsc.signInIntent
        startActivityForResult(intent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(
                    this@SignInActivity,
                    "some fucking error, ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.v(TAG, "error stack ${e.stackTrace}")
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
                if (account != null) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    //database operation
                    databaseOperation(account)
                }
            } else {
                Toast.makeText(baseContext, "Error!" + task.exception!!.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun databaseOperation(account: GoogleSignInAccount) {
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        var firstTime: Boolean

        firebaseDatabase.reference.child(Konstants.UIDS_HOSTS).child(mAuth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    firstTime = !snapshot.exists()
                    if (firstTime) {
                        //database insertion

                        firebaseDatabase.reference.child(Konstants.UIDS_HOSTS)
                            .child(mAuth.uid.toString()).setValue(account.email)

                        accountReference = firebaseDatabase.reference.child(Konstants.HOSTS)
                            .child(mAuth.uid.toString())
                        databaseInsertOperation(account)
                        Toast.makeText(
                            baseContext,
                            "Hello, " + account.displayName,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        var host: Host

                        accountReference = firebaseDatabase.reference.child(Konstants.HOSTS)
                        accountReference.child(mAuth.uid.toString()).child(Konstants.DATA)
                            .addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    host = snapshot.getValue(Host::class.java)!!
                                    Log.v(
                                        TAG,
                                        "here user " + host.name + ", " + host.email
                                    )
                                    editor.putString(Konstants.NAME, host.name)
                                    editor.putString(Konstants.EMAIL, host.email)
                                    editor.apply()
                                    Toast.makeText(
                                        baseContext,
                                        "welcome back ${host.name}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.v(TAG, "database here " + error.message)
                                }

                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.v(TAG, "database error ${error.message}")
                }

            })
    }

    private fun databaseInsertOperation(account: GoogleSignInAccount) {
        generalReference = firebaseDatabase.reference.child(Konstants.GENERAL)

        var hostCount: Int
        generalReference.child(Konstants.HOSTCOUNT)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    hostCount = snapshot.getValue(Int::class.java)!!
                    hostCount += 1
                    generalReference.child(Konstants.HOSTCOUNT).setValue(hostCount)
                    val host = Host(
                        account.displayName!!,
                        account.email!!
                    )
                    editor.putString(Konstants.NAME, host.name)
                    editor.putString(Konstants.EMAIL, host.email)
                    editor.apply()
                    accountReference.child(Konstants.DATA)
                        .setValue(host)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}