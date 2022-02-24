package com.example.myapplication.Facebook

import LoginCallback
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityFacebookLoginBinding
import com.facebook.*
import com.facebook.login.widget.LoginButton
import java.util.*


class FacebookLoginActivity : AppCompatActivity() {
    private var btn_facebook_login: LoginButton? = null
    private var mLoginCallback: LoginCallback? = null
    private var mCallbackManager: CallbackManager? = null
    private lateinit var binding: ActivityFacebookLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacebookLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mCallbackManager = CallbackManager.Factory.create()
        mLoginCallback = LoginCallback()
        btn_facebook_login = binding.loginButton
        btn_facebook_login!!.setReadPermissions(Arrays.asList("public_profile", "email"))
        btn_facebook_login!!.registerCallback(mCallbackManager, mLoginCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}