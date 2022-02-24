package com.example.myapplication.Naver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNaverLoginBinding
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler

class NaverLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNaverLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaverLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            this,
            "YG0VXunqHUZqtWwx8P7G"
            ,"YsROb5GC06"
            ,"Test"
            //,OAUTH_CALLBACK_INTENT
            // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        )

        val mOAuthLoginButton = binding.buttonOAuthLoginImg
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler)
        mOAuthLoginButton.setBgResourceId(R.drawable.btng_short)
    }


}