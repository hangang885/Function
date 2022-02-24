package com.example.myapplication

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityCustomGalleryBinding
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.util.concurrent.TimeUnit

class CustomGalleryActivity : AppCompatActivity() {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityCustomGalleryBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    private var uriArr = ArrayList<String>()
    private var customGalleryAdapter = CustomGalleryAdapter(this@CustomGalleryActivity, uriArr)
    val count: Int =0
    private val REQUEST_READ_EXTERMAL_STORAGE = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCustomGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // 권한 부여 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한 허용 안됨
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                // 이전에 이미 권한이 거부되었을 때 설명
                alert(
                    "사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다",
                    "권한이 필요한 이유"
                ) {
                    yesButton {
                        // 권한 요청
                        ActivityCompat.requestPermissions(
                            this@CustomGalleryActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERMAL_STORAGE
                        )
                    }
                    noButton { }
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERMAL_STORAGE
                )
            }
        } else {
            // 권한이 이미 허용됨

            getAllPhotos()
        }

        // 사용자가 권한 요청 시 호출되는 메서드
        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                REQUEST_READ_EXTERMAL_STORAGE -> {
                    if ((grantResults.isNotEmpty() && grantResults[0]
                                == PackageManager.PERMISSION_GRANTED)
                    ) {
                        getAllPhotos()
                    } else {
                        toast("권한 거부 됨")
                    }
                    return
                }
            }



        }
    }

    private fun getAllPhotos(){
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DURATION,
            MediaStore.Images.Media.SIZE
        )
        val selection = "${MediaStore.Images.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES).toString()
        )
        val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} DESC"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            sortOrder
        )


        val uriArr = ArrayList<String>()
        cursor?.use {
            val columnIndexId = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while(it.moveToNext()){
                val imageId = it.getString(columnIndexId)
                Log.d("imageId",imageId.toString())
                uriArr.add(
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(columnIndexId))
                        .toString()
                )

                Log.d("uriArr.add",
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(columnIndexId))
                        .toString())
            }
            cursor.close()
        }
        customGalleryAdapter = CustomGalleryAdapter(applicationContext, uriArr)
        binding.customGalleryGridview.numColumns = 4
        binding.customGalleryGridview.adapter = customGalleryAdapter

    }


}