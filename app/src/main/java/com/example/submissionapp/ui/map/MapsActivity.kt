package com.example.submissionapp.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.submissionapp.R
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var tokenPreferences: TokenPreferences
    private lateinit var mapsViewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        tokenPreferences = TokenPreferences(this)
        obtainViewModel()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun obtainViewModel(){
        val mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        mapsViewModel.getStories(tokenPreferences.getToken())
        mapsViewModel.listStory.observe(this){
            listStory ->
            setupViewMarker(listStory)
        }
    }

    private fun setupViewMarker(stories: List<StoryResponseItem>){
        val boundBuilder = LatLngBounds.Builder()
        stories.forEach{
            val latLng = LatLng(it.lat, it.lon)
            val desc100 = if(it.description.length > 100){
                it.description.substring(0,100) + "..."
            }else{
                it.description
            }
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(desc100)
            )
            boundBuilder.include(latLng)
        }

        val bounds = boundBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )

    }
}