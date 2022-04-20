package id.jyotisa.storyapp.ui.maps

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.databinding.ActivityMapsBinding
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.ui.ViewModelFactory
import id.jyotisa.storyapp.ui.login.LoginViewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
    private var boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setMapStyle()

        val pref = UserPreferences.getInstance(dataStore)
        val mapsViewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(this, pref))[LoginViewModel::class.java]

        loginViewModel.getAuthToken().observe(this
        ) { authToken: String ->
            mapsViewModel.getStories(authToken)
        }

        mapsViewModel.stories.observe(this) { listStory ->
            if (listStory.isEmpty()){
                Toast.makeText(
                    this,
                    "Tidak ada story dengan Lat dan Long yang ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                for (story in listStory) {
                    val latLng = story.lat?.let { story.lon?.let { it1 -> LatLng(it, it1) } }
                    latLng?.let {
                        MarkerOptions()
                            .position(it)
                            .title(story.name)
                            .snippet(story.description)
                    }?.let {
                        mMap.addMarker(
                            it
                        )
                    }
                    latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 5f) }
                        ?.let { mMap.animateCamera(it) }

                    latLng?.let { boundsBuilder.include(it) }
                    val bounds: LatLngBounds = boundsBuilder.build()
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 64))
                }
            }
        }

        mapsViewModel.getToastObserver().observe(this) { message ->
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e("Set Style Info", "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e("Set Style Info", "Can't find style. Error: ", exception)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}