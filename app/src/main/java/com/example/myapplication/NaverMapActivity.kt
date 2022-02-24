package com.example.myapplication

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.databinding.ActivityNaverMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource


class NaverMapActivity : OnMapReadyCallback,FragmentActivity(),NaverMap.OnLocationChangeListener {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var binding:ActivityNaverMapBinding
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNaverMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        val coord = LatLng(37.5670135, 126.9783740)
        mapFragment.getMapAsync(this)
        Toast.makeText(
            this,
            "위도: " + coord.latitude + ", 경도: " + coord.longitude,
            Toast.LENGTH_SHORT
        ).show()

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)




    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
    fun addMarker(naverMap: NaverMap){
        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = naverMap
    }
    override fun onMapReady(naverMap: NaverMap) {

        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.locationSource = locationSource
        addMarker(naverMap)
        var uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isTiltGesturesEnabled = true
        uiSettings.isRotateGesturesEnabled = true


    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None


            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onLocationChange(p0: Location) {
        Log.d("naverMap.cameraPosition.target.latitude",naverMap.cameraPosition.target.latitude.toString())
        Log.d("naverMap.cameraPosition.target.longitude",naverMap.cameraPosition.target.longitude.toString())
        Log.d("p0.latitude",p0.latitude.toString())
        Log.d("p0longitude",p0.longitude.toString())
    }
}