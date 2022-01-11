package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityBiometricBinding
import java.util.concurrent.Executor
import android.widget.Toast
import androidx.biometric.BiometricPrompt.PromptInfo


class BiometricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiometricBinding

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBiometricBinding.inflate(layoutInflater)
        var view = binding.root

        setContentView(view)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                     errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "인증에러!", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "인증성공!", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "인증실패!", Toast.LENGTH_SHORT).show()
                }

            })


        promptInfo = PromptInfo.Builder()
            .setTitle("지문 인증")
            .setNegativeButtonText("취소")
            .setDeviceCredentialAllowed(false)
            .build()

        biometricPrompt.authenticate(promptInfo)

    }
}