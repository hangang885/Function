package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    var mBinding : ActivityLoginBinding? = null
    val binding  get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = LoginDatabase.getInstance(applicationContext)

        com.kakao.sdk.common.util.Utility.getKeyHash(this)
        if(AutoLogin.getUserId(applicationContext).isNotEmpty()){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            var result: List<Login>
            val id = binding.loginId.text.toString()
            val pw = binding.loginPw.text.toString()
            CoroutineScope(Dispatchers.IO).launch {  //코루틴 사용
                result = db!!.loginDao().idpwd(id,pw)
                Log.d("result",result.toString())
                if(result.isEmpty())
                {
                    val newAdmin = Login("admin2","12345")
                    db.loginDao().insert(newAdmin)
                }

                result = db.loginDao().idpwd(id,pw)
                if(result.isNotEmpty())
                {
                    AutoLogin.setUserId(applicationContext,id)
                    AutoLogin.setUserPass(applicationContext,pw)
                    val intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.signUp.setOnClickListener {
            startActivity(Intent(applicationContext,SignupActivity::class.java))
        }
    }
}
