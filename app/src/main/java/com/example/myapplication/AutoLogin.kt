package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

object AutoLogin {
    private val MY_ACCOUNT : String = "account"

    /*
      getSharedPreferences(key, mode)

     MODE_PRIVATE : 자기 앱내에서만 사용할때 (0을 입력해도 된다)

     MODE_WORLD_READABLE : 다른 앱에서 읽기 가능

     MODE_WORLD_WRITEABLE : 다른 앱에서 쓰기 가능*/

    //UserId 설정
    fun setUserId(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.commit()
    }
    //UserId 보내기
    fun getUserId(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }
    //UserPass 설정
    fun setUserPass(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PASS", input)
        editor.commit()
    }
    //UserPass 보내기
    fun getUserPass(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PASS", "").toString()
    }
    //User 초기화
    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

}
