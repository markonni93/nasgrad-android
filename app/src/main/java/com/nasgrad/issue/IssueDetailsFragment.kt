package com.nasgrad.issue

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.nasgrad.adapter.TypesSpinnerAdapter
import com.nasgrad.model.Category
import com.nasgrad.model.Type
import com.nasgrad.nasGradApp.R
import kotlinx.android.synthetic.main.create_issue_bottom_navigation_layout.*
import kotlinx.android.synthetic.main.fragment_issue_details.*
import timber.log.Timber


class IssueDetailsFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (activity as CreateIssueActivity).supportActionBar?.title = getString(R.string.issue_details_title)
        (activity as CreateIssueActivity).enableHomeButton(false)
        return inflater.inflate(R.layout.fragment_issue_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTypesSpinner()

        ibArrowLeft.visibility = View.VISIBLE
        tvPageIndicator.text = String.format(getString(R.string.create_issue_page_indicator), 2)

        ibArrowLeft.setOnClickListener(this)
        ibArrowRight.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            ibArrowLeft.id -> {
                Timber.d("black click")
                (activity as CreateIssueActivity).openPreviousFragment()
            }
            ibArrowRight.id -> {
                // update issue
                val issue = (activity as CreateIssueActivity).issue
                issue.title = tvIssueTitle.text.toString()

                val type = spinnerTypes.selectedItem as Type
                issue.issueType = type.name

                val list = mutableListOf<String>()
                type.categories?.forEach { list.add(it?.name!!) }
                issue.categories = list

                issue.description = etIssueDescription.text.toString()

                Timber.d("Issue: $issue")
                (activity as CreateIssueActivity).setFragment(R.id.mainContent, PreviewIssueFragment())
            }
        }
    }

    private fun initTypesSpinner() {
        val firstCategories = arrayListOf<Category>()
        firstCategories.add(Category("1", "Kategorija 1", "Opis 1", "email1@gmail.com"))
        firstCategories.add(Category("2", "Kategorija 2", "Opis 2", "email2@gmail.com"))
        firstCategories.add(Category("22", "Kategorija 22", "Opis 22", "email22@gmail.com"))

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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Timber.d("onNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedType = parent?.getItemAtPosition(position) as Type
        Timber.d("onItemSelected $selectedType")

        val categories = selectedType.categories
        if (categories != null) {
            when {
                categories.size == 1 -> {
                    tvFirstCategory.visibility = View.VISIBLE
                    tvFirstCategory.text = categories[0]?.name
                    tvSecondCategory.visibility = View.GONE
                    tvThirdCategory.visibility = View.GONE
                }
                categories.size == 2 -> {
                    tvFirstCategory.visibility = View.VISIBLE
                    tvFirstCategory.text = categories[0]?.name
                    tvSecondCategory.visibility = View.VISIBLE
                    tvSecondCategory.text = categories[1]?.name
                    tvThirdCategory.visibility = View.GONE
                }
                categories.size >= 3 -> {
                    tvFirstCategory.visibility = View.VISIBLE
                    tvFirstCategory.text = categories[0]?.name
                    tvSecondCategory.visibility = View.VISIBLE
                    tvSecondCategory.text = categories[1]?.name
                    tvThirdCategory.visibility = View.VISIBLE
                    tvThirdCategory.text = categories[2]?.name
                }
                else -> {
                    tvFirstCategory.visibility = View.GONE
                    tvSecondCategory.visibility = View.GONE
                    tvThirdCategory.visibility = View.GONE
                }
            }
        }
    }
}
