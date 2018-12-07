package com.nasgrad.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.nasgrad.CreateIssueActivity
import com.nasgrad.adapter.TypesSpinnerAdapter
import com.nasgrad.model.Category
import com.nasgrad.model.Type
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import kotlinx.android.synthetic.main.fragment_issue_details.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class IssueDetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (activity as CreateIssueActivity).supportActionBar?.title = getString(R.string.issue_details_title)

        return inflater.inflate(R.layout.fragment_issue_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTypesSpinner()
        setPageIndicator(2)
    }

    private fun initTypesSpinner() {
        val firstCategories = arrayListOf<Category>()
        firstCategories.add(Category("1", "Kategorija 1", "Opis 1", "email1@gmail.com"))
        firstCategories.add(Category("2", "Kategorija 2", "Opis 2", "email2@gmail.com"))

        val secondCategories = arrayListOf<Category>()
        secondCategories.add(Category("3", "Kategorija 3", "Opis 3", "email3@gmail.com"))
        secondCategories.add(Category("4", "Kategorija 4", "Opis 4", "email4@gmail.com"))

        val firstType = Type("1", "Tip 1", "Opis 1", firstCategories)
        val secondType = Type("2", "Tip 2", "Opis 2", secondCategories)

        val types = arrayListOf<Type>()
        types.add(firstType)
        types.add(secondType)

        val adapter = TypesSpinnerAdapter((activity as CreateIssueActivity), R.layout.types_spinner_item, types)
        adapter.setDropDownViewResource(R.layout.types_spinner_item)

        spinnerTypes.adapter = adapter
        spinnerTypes.onItemSelectedListener = this
    }

    private fun setPageIndicator(pageId: Int) {
        tvPageIndicator.text = String.format(getString(R.string.create_issue_page_indicator), pageId)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedType = parent?.getItemAtPosition(position) as Type
        Log.d("CreateIssueActivity", "Selected type " + selectedType.name)

        val categories = selectedType.categories
        if (categories != null && categories.isNotEmpty()) {

            if (categories[0] != null) {
                tvFirstCategory.visibility = View.VISIBLE
                tvFirstCategory.text = categories[0]?.name
            } else {
                tvFirstCategory.visibility = View.GONE
            }

            if (categories[1] != null) {
                tvFirstCategory.visibility = View.VISIBLE
                tvFirstCategory.text = categories[1]?.name
            } else {
                tvFirstCategory.visibility = View.GONE
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IssueDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                IssueDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
