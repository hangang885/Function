package com.example.myapplication


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle;
import android.util.Log
import android.view.View
import android.view.ViewGroup;
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityMapBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.*
import net.daum.android.map.coord.MapCoord
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPoint.*
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapReverseGeoCoder
import org.jetbrains.anko.support.v4.slidingPaneLayout
import kotlin.math.roundToLong


class MapActivity : AppCompatActivity(), MapReverseGeoCoder.ReverseGeoCodingResultListener,
    MapView.CurrentLocationEventListener, MapView.MapViewEventListener {

    private var mBinding: ActivityMapBinding? = null
    private val binding get() = mBinding!!
    private lateinit var hospitalNameList: Array<String>
    lateinit var hospitalAddressList: Array<String>
    var hospitalLatitude: MutableList<Double> = mutableListOf<Double>()
    var hospitalLongitude: MutableList<Double> = mutableListOf<Double>()
    var hospitalName: MutableList<String> = mutableListOf<String>()
    var hospitalAddress: MutableList<String> = mutableListOf<String>()


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hospitalNameList = resources.getStringArray(R.array.hospitalList)
        for (element in hospitalNameList) {
            hospitalName.add(element)
        }

        hospitalAddressList = resources.getStringArray(R.array.hospitalAddressList)
        for (i in 0 until hospitalAddressList.size) {
            hospitalAddress.add(hospitalAddressList[i])
        }
        requestPermission()
        var mapView: MapView = MapView(this)
        var mapViewContainer: ViewGroup = binding.mapView

        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        // 중심점 변경
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(2, true);
        // 줌 인
        mapView.zoomIn(true);


        var address = "서울시 관악구 남부순환로 1811"
//        var latitude = getLocation(address)[0].latitude
//        var longitude = getLocation(address)[0].longitude

        mapView.setCurrentLocationEventListener(this)
        mapView.setMapViewEventListener(this)

        CoroutineScope(Dispatchers.IO).launch {


            Log.d("han_Start", System.currentTimeMillis().toString())
            getAddress()
            Log.d("han_End", System.currentTimeMillis().toString())


            async {
                CoroutineScope(Dispatchers.Default).launch {
                    AddMarker(mapView)
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
            var locationManager: LocationManager =
                applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

            var location: Location =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
            var latitude = location.latitude
            var longitude = location.longitude

            Log.d("latitude", latitude.toString())
            Log.d("longitude", longitude.toString())

            getDistance(latitude, longitude)
            }
        }
        mapView.zoomOut(true);
        mapView.setShowCurrentLocationMarker(true)
        mapView.setDefaultCurrentLocationMarker()
        Log.d("showlocation", mapView.isShowingCurrentLocationMarker().toString())
        mapViewContainer.addView(mapView)


        binding.panel.addPanelSlideListener( object : SlidingUpPanelLayout.PanelSlideListener{

            override fun onPanelSlide(panel: View?, slideOffset: Float) {
            }


            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {

                  if(newState!!.name == "Collapsed"){

                    // 닫혔을때 처리하는 부분



                }else if(newState!!.name == "Expanded"){



                    // 열렸을때 처리하는 부분

                }



            }

        })

    }

    fun getDistance(start_latitude: Double, start_longitude: Double) {
        var closeLocation_latitude: Double = 0.0
        var closeLocation_longitude: Double = 0.0
        var locationA: Location = Location("point A")
        locationA.latitude = start_latitude
        locationA.longitude = start_longitude
        var distanceArray = mutableListOf<Int>()
        var distanceName = mutableListOf<String>()
        for (i in 0 until hospitalAddress.size) {
            var locationB: Location = Location("point B")
            locationB.latitude = hospitalLatitude[i]
            locationB.longitude = hospitalLongitude[i]
            distanceName.add(hospitalName[i])
            distanceArray.add(locationA.distanceTo(locationB).toInt())

        }
        distanceArray.sort()
        distanceName.sort()
        var text = ""
        for (k in 0..5) {
            Log.d("distance", distanceArray[k].toString())
            var distance1 = (distanceArray[k] / 1000)
            var distance2 = (distanceArray[k] % 1000)
            text += "${distanceName[k]} : $distance1.$distance2 km \n"
            Log.d("distance", "$distance1.$distance2 km")
        }
        var textview= findViewById<TextView>(R.id.text1)

        textview.setText(text)
    }

    fun getLocation(address: String): MutableList<Address> {
        var addressList = Geocoder(applicationContext).getFromLocationName(address, 1)
        return addressList
    }


    private fun requestPermission(): Boolean {
        if ((ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )) != PackageManager.PERMISSION_GRANTED || (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )) != PackageManager.PERMISSION_GRANTED
        ) {

            return if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 100
                )
                true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 100
                )
                false
            }
        }

        return true
    }


    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
        //내 위치 찾기
        val mapPointGeo = p1!!.mapPointGeoCoord

        //내 위치 주소 찾기
        //kakaoKey는 Native APP KEY를 의미한다
        var mReverseGeoCoder = MapReverseGeoCoder(
            "60f7b9a8c0b841ae255f8b93d3ba4ecf",
            p0!!.mapCenterPoint,
            this@MapActivity,
            this@MapActivity
        )
//        p0!!.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord( mapPointGeo.latitude,  mapPointGeo.longitude), 1, true);
        Log.d(
            "Han_mapPointGeo.latitude.toString()",
            "::" + p1.mapPointGeoCoord.latitude.toString()
        )
        Log.d(
            "Han_mapPointGeo.latitude.toString()",
            "::" + p1.mapPointGeoCoord.latitude.toString()
        )
        Log.d(
            "Han_mReverseGeoCoder.startFindingAddress()",
            "::" + mReverseGeoCoder.startFindingAddress().toString()
        )


    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
    }

    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {

    }

    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
    }

    suspend fun AddMarker(mapView: MapView) {
        for (i in 0 until hospitalLatitude.size) {
            var marker = MapPOIItem()
            marker.itemName = hospitalName[i]
            marker.mapPoint = mapPointWithGeoCoord(hospitalLatitude[i], hospitalLongitude[i])
            marker.markerType = MapPOIItem.MarkerType.RedPin
            marker.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
            mapView.addPOIItem(marker)
        }

    }

    suspend fun getAddress() {
        for (i in 0 until hospitalAddress.size) {
            hospitalLatitude.add(
                Geocoder(applicationContext).getFromLocationName(
                    hospitalAddress[i],
                    1
                )[0].latitude
            )
            hospitalLongitude.add(
                Geocoder(applicationContext).getFromLocationName(
                    hospitalAddress[i],
                    1
                )[0].longitude
            )
        }

    }

    override fun onMapViewInitialized(p0: MapView?) {
        if (p0 != null) {
            Log.d(
                "Seon_onMapViewInitialized_latitude",
                "::" + p0.mapCenterPoint.mapPointGeoCoord.latitude.toString()
            )
        }
        if (p0 != null) {
            Log.d(
                "Seon_onMapViewInitialized_longitude",
                "::" + p0.mapCenterPoint.mapPointGeoCoord.longitude.toString()
            )
        }
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.d(
            "Seon_onMapViewCenterPointMoved_latitude",
            "::" + p1!!.mapPointGeoCoord.latitude.toString()
        )
        Log.d(
            "Seon_onMapViewCenterPointMoved_longitude",
            "::" + p1!!.mapPointGeoCoord.longitude.toString()
        )
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Log.d(
            "Seon_onMapViewMoveFinished_latitude",
            "::" + p1!!.mapPointGeoCoord.latitude.toString()
        )
        Log.d(
            "Seon_onMapViewMoveFinished_longitude",
            "::" + p1!!.mapPointGeoCoord.longitude.toString()
        )
    }


}
