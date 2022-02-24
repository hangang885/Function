package com.example.myapplication


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityMapBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPoint.*
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView


class MapActivity : AppCompatActivity(), MapReverseGeoCoder.ReverseGeoCodingResultListener,
    MapView.CurrentLocationEventListener, MapView.MapViewEventListener {

    // 병원 리스트
    private lateinit var hospitalNameList: Array<String>
    lateinit var hospitalAddressList: Array<String>
    var hospitalLatitude: MutableList<Double> = mutableListOf()
    var hospitalLongitude: MutableList<Double> = mutableListOf()
    var hospitalName: MutableList<String> = mutableListOf()
    var hospitalAddress: MutableList<String> = mutableListOf()


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //String 파일에 있는값 불러오기
        hospitalNameList = resources.getStringArray(R.array.hospitalList)
//        for (element in hospitalNameList) {
//            hospitalName.add(element)
//        }
        hospitalNameList.forEach { i ->
            hospitalName.add(i)
        }

        //String 파일에 있는 값 불러오기
        hospitalAddressList = resources.getStringArray(R.array.hospitalAddressList)
//        for (i in 0 until hospitalAddressList.size) {
//            hospitalAddress.add(hospitalAddressList[i])
//        }
        hospitalAddressList.forEach { i ->
            hospitalAddress.add(i)
        }

        //permission 확인
        requestPermission()
        val mapView = MapView(this)
        val mapViewContainer: ViewGroup = binding.mapView

        //맵뷰 시작부터 트래킹 모드 온
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        // 중심점 변경
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(2, true)
        // 줌 인
        mapView.zoomIn(true)
        //맵뷰에 리스너장착
        mapView.setCurrentLocationEventListener(this)
        mapView.setMapViewEventListener(this)

        CoroutineScope(Dispatchers.IO).launch {

            // 주소-> 좌표 변환 메소드
            Log.d("Han_Start", System.currentTimeMillis().toString())
            getAddress()
            Log.d("Han_End", System.currentTimeMillis().toString())


            async {
                CoroutineScope(Dispatchers.Default).launch {
                    // 병원들 마커 찍기
                    AddMarker(mapView)
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                //현재 위치값 구하기
                val locationManager: LocationManager =
                    applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

                val location: Location =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                val latitude = location.latitude
                val longitude = location.longitude

                Log.d("Han_latitude", latitude.toString())
                Log.d("Han_longitude", longitude.toString())
                //구한값과 병원들 좌표 계산해서 가까운 병원 5개 찾는 메소드
                getDistance(latitude, longitude)
            }
        }
        // 지도 화면을 한단계 축소한다 ( 더 넓은 영역이 화면에 나타남) 확대 / 축소 레벨 값이 1 즈가됨
        mapView.zoomOut(true)
        // 현 위치를 표시하는 아이콘(마커)를 화면에 표시할지 여부를 설정
        mapView.setShowCurrentLocationMarker(true)
        // 기본 제공되는 현위치 아이콘 이미지를 사용
        mapView.setDefaultCurrentLocationMarker()
        Log.d("Han_showlocation", mapView.isShowingCurrentLocationMarker.toString())
        mapViewContainer.addView(mapView)

        // 슬라이딩패널 레이아웃 리스너
        binding.panel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {

            override fun onPanelSlide(panel: View?, slideOffset: Float) {
            }


            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {

                if (newState!!.name == "Collapsed") {

                    // 닫혔을때 처리하는 부분


                } else if (newState.name == "Expanded") {


                    // 열렸을때 처리하는 부분

                }


            }

        })

    }

    fun getDistance(start_latitude: Double, start_longitude: Double) {
        //현재위치 받을 로케이션 변수
        val locationA = Location("point A")
        locationA.latitude = start_latitude
        locationA.longitude = start_longitude
        val distanceArray = mutableListOf<Int>()
        val distanceName = mutableListOf<String>()
        for (i in 0 until hospitalAddress.size) {
            // 병원위치 받을 로케이션 변수
            val locationB = Location("point B")
            locationB.latitude = hospitalLatitude[i]
            locationB.longitude = hospitalLongitude[i]
            distanceName.add(hospitalName[i])
            distanceArray.add(locationA.distanceTo(locationB).toInt())

        }
        //리스트 오름차순으로 정렬
        distanceArray.sort()
        distanceName.sort()
        var text = ""
        for (k in 0..5) {
            Log.d("Han_distance", distanceArray[k].toString())
            // m 값 km 값으로 변환
            val distance1 = (distanceArray[k] / 1000)
            val distance2 = (distanceArray[k] % 1000)
            text += "${distanceName[k]} : $distance1.$distance2 km \n"
            Log.d("Han_distance", "$distance1.$distance2 km")
        }
        val textview = findViewById<TextView>(R.id.text1)

        textview.text = text
    }

//    fun getLocation(address: String): MutableList<Address> {
//        var addressList = Geocoder(applicationContext).getFromLocationName(address, 1)
//        return addressList
//    }


    private fun requestPermission(): Boolean {
        // 권한 확인
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
        p1!!.mapPointGeoCoord

        //내 위치 주소 찾기
        //kakaoKey는 Native APP KEY를 의미한다
        val mReverseGeoCoder = MapReverseGeoCoder(
            "60f7b9a8c0b841ae255f8b93d3ba4ecf",
            p0!!.mapCenterPoint,
            this@MapActivity,
            this@MapActivity
        )
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

    //단말의 방향 각도값을  통보받을 수 있다.
    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
    }

    // 현위치 갱신 작업에 실패한 경우 호출
    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
    }

    // 현위치 트래킹 기능이 사용자에 의해 취소된 경우 호출된다
    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
    }

    // 주소를 찾은 경우 호출된다
    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {
    }

    // Reverse Geo-Coding 서비스 호출에 실패한 경우 호출된다.
    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
    }

    fun AddMarker(mapView: MapView) {
        for (i in 0 until hospitalLatitude.size) {
            val marker = MapPOIItem()
            // 마커 이름설정
            marker.itemName = hospitalName[i]
            // 마커 위치 설정
            marker.mapPoint = mapPointWithGeoCoord(hospitalLatitude[i], hospitalLongitude[i])
            // 마커 색상 설정
            marker.markerType = MapPOIItem.MarkerType.RedPin
            // 마커 클릭 했을때 색상 설정
            marker.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
            mapView.addPOIItem(marker)
        }

    }

    fun getAddress() {
        // getFromLocationName == 주소 -> 좌표변환
        // getFromLocation == 좌표 -> 주소 변환
//        for (i in 0 until hospitalAddress.size) {
//            // 주소 좌표로 변환해서 위도 리스트에 ADD
//            hospitalLatitude.add(
//                Geocoder(applicationContext).getFromLocationName(
//                    hospitalAddress[i],
//                    1
//                )[0].latitude
//            )
//            // 주소 좌표로 변환해서 경도 리스트에 ADD
//            hospitalLongitude.add(
//                Geocoder(applicationContext).getFromLocationName(
//                    hospitalAddress[i],
//                    1
//                )[0].longitude
//            )
//        }

        hospitalAddress.forEach { i ->
            hospitalLatitude.add(
                Geocoder(applicationContext).getFromLocationName(
                    i,
                    1
                )[0].latitude
            )
            // 주소 좌표로 변환해서 경도 리스트에 ADD
            hospitalLongitude.add(
                Geocoder(applicationContext).getFromLocationName(
                    i,
                    1
                )[0].longitude
            )
        }


    }

    // MapView 가 사용가능 한 상태가 되었음을 알려준다.
    override fun onMapViewInitialized(p0: MapView?) {
        if (p0 != null) {
            Log.d(
                "Han_onMapViewInitialized_latitude",
                "::" + p0.mapCenterPoint.mapPointGeoCoord.latitude.toString()
            )
        }
        if (p0 != null) {
            Log.d(
                "Han_onMapViewInitialized_longitude",
                "::" + p0.mapCenterPoint.mapPointGeoCoord.longitude.toString()
            )
        }
    }

    // 지도 중심좌표가 이동한경우 호출된다.
    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.d(
            "Han_onMapViewCenterPointMoved_latitude",
            "::" + p1!!.mapPointGeoCoord.latitude.toString()
        )
        Log.d(
            "Han_onMapViewCenterPointMoved_longitude",
            "::" + p1.mapPointGeoCoord.longitude.toString()
        )
    }

    // 지도 확대/축소 레벨이 변경된 경우 호출된다.
    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
    }

    // 사용자가 지도 위를 터치한 경우 호출된다.
    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
    }

    // 사용자가 지도 위 한 지점을 더블 터치한 경우 호출된다.
    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
    }

    // 사용자가 지도 위 한 지점을 길게 누른 경우 호출된다.
    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
    }

    // 사용자가 지도 드래그를 시작한 경우 호출된다.
    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
    }

    // 사용자가 지도 드래그를 끝낸 경우 호출된다.
    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
    }

    // 지도 이동이 완료된 경우 호출된다.
    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Log.d(
            "Han_onMapViewMoveFinished_latitude",
            "::" + p1!!.mapPointGeoCoord.latitude.toString()
        )
        Log.d(
            "Han_onMapViewMoveFinished_longitude",
            "::" + p1.mapPointGeoCoord.longitude.toString()
        )
    }


}
