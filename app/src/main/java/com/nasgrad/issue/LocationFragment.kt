package com.nasgrad.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import timber.log.Timber


class LocationFragment : Fragment(), OnMapReadyCallback, View.OnClickListener, GoogleMap.OnCameraIdleListener {

    private var map: GoogleMap? = null

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
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        this.map?.setOnCameraIdleListener(this)
    }

    override fun onCameraIdle() {
        Timber.d("onCameraIdle")
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
