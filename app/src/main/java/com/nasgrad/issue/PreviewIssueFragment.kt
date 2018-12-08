package com.nasgrad.issue

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nasgrad.DetailActivity
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import kotlinx.android.synthetic.main.fragment_preview_issue.*


class PreviewIssueFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_preview_issue, container, false)

        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.screen_title_issue_summary)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ibArrowLeft.visibility = View.VISIBLE
        tvPageIndicator.text = String.format(getString(R.string.create_issue_page_indicator), 4)
        val issue = (activity as CreateIssueActivity).issue
        issue_title.text = issue.title
        typePreview.text = "Tip problema: ${issue.issueType}"
        categoryPreview.text = "Kategorije: ${issue.categories?.get(0).toString()}, ${issue.categories?.get(1).toString()}, ${issue.categories?.get(2).toString()}"

        ibArrowLeft.setOnClickListener(this)
        ibArrowRight.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            ibArrowLeft.id -> {
                (activity as CreateIssueActivity).openPreviousFragment()
            }
            ibArrowRight.id -> {
                // update issue
                val issue = (activity as CreateIssueActivity).issue
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("ITEM_TITLE", issue.title)
                intent.putExtra("ITEM_DESCRIPTION", issue.description)
                intent.putExtra( "ITEM_TYPE", issue.issueType)
                startActivity(intent)
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
    }

}
