package com.example.myapplication

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient.Companion.instance

class GlobalApplication : Application() {
    private lateinit var instance: GlobalApplication
    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSdk.init(this, "726e909b6cc68748ab227b96ce7fce72")
    }
}
