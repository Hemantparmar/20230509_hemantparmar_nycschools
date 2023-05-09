package com.hemant_20230509_hemantparmar_nycschools.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hemant_20230509_hemantparmar_nycschools.R
import com.hemant_20230509_hemantparmar_nycschools.helper.lgd
import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.databinding.FragmentMainBinding
import com.hemant_20230509_hemantparmar_nycschools.main.adapter.SchoolClickListener
import com.hemant_20230509_hemantparmar_nycschools.main.adapter.SchoolItemAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A placeholder fragment for schools information
 */
@AndroidEntryPoint
class SchoolInfoFragment: Fragment(), SchoolClickListener {

    // view binding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    // view models
    private val schoolInfoVM: SchoolInfoViewModel by viewModels()
    private val mainVM: MainViewModel by activityViewModels()

    // recyclerView clickListener
    override fun onSchool_item_click(school: School) {
        lgd("==== searching dbn: ${school.dbn}")
        // update view
        mainVM.display.postValue(InfoStatus.SAT_SCORES)

        // display scores by school's dbn
        val scoreFragment =
            SchoolScoresFragment.newInstance(school.dbn, school.schoolName)
        // show fragment
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.scores_display, scoreFragment)
            .addToBackStack(scoreFragment.javaClass.simpleName)
            .setCustomAnimations(
                R.anim.slide_up,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val recView: RecyclerView = binding.recyclerView
        binding.sectionLabel.visibility = View.GONE

        // preset data
        schoolInfoVM.apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }

        // observer
        schoolInfoVM.text.observe(viewLifecycleOwner) {
            binding.sectionLabel.text = it
        }

        // display schools by their name into 5 different categories
        schoolInfoVM.schoollist.observe(viewLifecycleOwner) {
            val mText = binding.sectionLabel.text
            val mSec = mText.last() //find the section#

            lgd("Section # at ${mSec}")
            if (it.size > 0) {
                var mList = listOf<School>()

                // divide school list into sections
                when (mSec) {
                    '1' -> { // ABCDE
                        mList = it.sortedBy { it.schoolName }
                            .filter { it.schoolName[0] < 'F' }
                    }
                    '2' -> { // FGHIJ
                        mList = it.sortedBy { it.schoolName }
                            .filter { 'E' < it.schoolName[0] && it.schoolName[0] < 'K' }
                    }
                    '3' -> { // KLMNO
                        mList = it.sortedBy { it.schoolName }
                            .filter { 'J' < it.schoolName[0] && it.schoolName[0] < 'P' }
                    }
                    '4' -> { // PQRST
                        mList = it.sortedBy { it.schoolName }
                            .filter { 'O' < it.schoolName[0] && it.schoolName[0] < 'U' }
                    }
                    else -> { // U-Z0-9
                        mList = it.sortedBy { it.schoolName }
                            .filter { it.schoolName[0] > 'T' }
                    }
                }
                recView.adapter = SchoolItemAdapter(mList, this)
            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        // tab section
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): SchoolInfoFragment {
            return SchoolInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}