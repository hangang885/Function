package com.example.myapplication.Google

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import bolts.Task
import com.example.myapplication.databinding.ActivityGoogleLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class GoogleLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoogleLoginBinding
    val RC_SIGN_IN = 1

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        binding.signInButton.setOnClickListener {
            onClick(binding.signInButton)
        }

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    fun onClick(view: View) {
        when (view) {
            binding.signInButton -> {
                signIn()
            }
        }
    }

    fun signIn() {
        val singInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(singInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data) as Task<GoogleSignInAccount>


        }
    }
}