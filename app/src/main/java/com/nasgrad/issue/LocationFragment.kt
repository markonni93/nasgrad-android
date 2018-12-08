package com.nasgrad.issue

import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import timber.log.Timber
import java.io.IOException


class LocationFragment : Fragment(), OnMapReadyCallback, View.OnClickListener, GoogleMap.OnCameraIdleListener {

    private var map: GoogleMap? = null

    private lateinit var currentLocation: LatLng

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        (activity as CreateIssueActivity).setActionBarTitle(getString(R.string.issue_address_title))
        ibArrowLeft.visibility = View.VISIBLE
        tvPageIndicator.text = String.format(getString(R.string.create_issue_page_indicator), 3)

        ibArrowLeft.setOnClickListener(this)
        ibArrowRight.setOnClickListener(this)

        fusedLocationProviderClient = FusedLocationProviderClient(activity as CreateIssueActivity)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        this.map?.setOnCameraIdleListener(this)

        getCurrentLocation()
        updateUI()
    }

    override fun onCameraIdle() {
        Timber.d("onCameraIdle")
        val latLng = map?.cameraPosition?.target!!
        val geocoder = Geocoder((activity as CreateIssueActivity))

        try {
            val addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addressList != null && addressList.size > 0) {
                val locality = addressList[0].getAddressLine(0)
                val country = addressList[0].countryName
                if (!locality.isEmpty() && !country.isEmpty()) {
                    Timber.d("$locality, $country")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getCurrentLocation() {
        try {
            if ((activity as CreateIssueActivity).permissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation!!
                locationResult.addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                        LatLng(currentLocation.latitude, currentLocation.longitude), 15f))

                    } else {
                        Timber.d("Location is null")
                    }
                }
            }
        } catch (e: SecurityException) {
            Timber.e(e, "Exception")
        }
    }

    private fun updateUI() {
        if (map == null) {
            return
        }
        try {
            if ((activity as CreateIssueActivity).permissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {
            Timber.e(e, "Exception")
        }

    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            ibArrowLeft.id -> {
                Timber.d("black click")
                (activity as CreateIssueActivity).openPreviousFragment()
            }
            ibArrowRight.id -> {
                (activity as CreateIssueActivity).setFragment(R.id.mainContent, PreviewIssueFragment())
            }
        }
    }
}
