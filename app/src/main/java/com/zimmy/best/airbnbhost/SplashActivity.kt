package com.zimmy.best.airbnbhost

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.zimmy.best.airbnbhost.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var mAuth: FirebaseAuth
    private var isMain: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)


        mAuth = FirebaseAuth.getInstance()

        val handler = Handler()
        handler.postDelayed({
            //check for the pre sign in
            if (!isMain) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
//                Toast.makeText(this, "move to main", Toast.LENGTH_SHORT).show();
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }, 1500)

    }

    override fun onStart() {
        super.onStart()
        isMain = isUserSignIn()
    }

    private fun isUserSignIn(): Boolean {
        val currentUser = mAuth.currentUser
        return currentUser != null
    }

    private fun isUserSignInPrev(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        return account != null
    }
}