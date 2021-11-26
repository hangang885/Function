package com.example.myapplication.Naver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNaverLoginBinding
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton

class NaverLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNaverLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaverLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
            this,
            "YG0VXunqHUZqtWwx8P7G"
            ,"YsROb5GC06"
            ,"Test"
            //,OAUTH_CALLBACK_INTENT
            // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );

        var mOAuthLoginButton = binding.buttonOAuthLoginImg
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setBgResourceId(R.drawable.btng_short);

    }


}