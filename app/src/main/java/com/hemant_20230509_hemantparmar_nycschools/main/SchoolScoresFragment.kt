package com.hemant_20230509_hemantparmar_nycschools.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hemant_20230509_hemantparmar_nycschools.databinding.FragmentSchoolScoresBinding
import com.hemant_20230509_hemantparmar_nycschools.helper.lgd
import com.hemant_20230509_hemantparmar_nycschools.helper.NOT_FOUND
import com.hemant_20230509_hemantparmar_nycschools.main.InfoStatus.*
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class SchoolScoresFragment : Fragment() {

    // view binding
    private var _binding: FragmentSchoolScoresBinding? = null
    private val binding get() = _binding!!

    // view models
    private val scoresVM: SchoolScoresViewModel by viewModels()
    private val mainVM: MainViewModel by activityViewModels()

    // school name
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // redirect back button
        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding
        _binding = FragmentSchoolScoresBinding.inflate(inflater, container, false)
        val root = binding.root

        // preset data from arguement
        scoresVM.apply {
            setKey(arguments?.getString(ARG_SCHOOL_NUMBER) ?: "0")
        }
        // get school name
        name = arguments?.getString(ARG_SCHOOL_NAME) ?: "0"

        // set back button
        binding.fabBack.setOnClickListener{
            onBackPressed()
        }

        scoresVM.school.observe(viewLifecycleOwner) {
            if (it != null) {
                // update UI
                binding.schoolTitle.schoolName.text = it.schoolName
                binding.addressTv.text = it.location
                binding.cityTv.text = it.city
                binding.stateTv.text = it.stateCode
                binding.zipTv.text = it.zip
                binding.phoneTv.text = it.phoneNumber
                binding.faxTv.text = it.faxNumber
                binding.emailTv.text = it.schoolEmail
                binding.websiteTv.text = it.website
            }
        }

        // observer
        scoresVM.scores.observe(viewLifecycleOwner) {
            if (it != null) {
                lgd("===== update: $it")
                // update UI
                binding.testTakersRecord.text = it.numOfSatTestTakers
                binding.criticalReadingRecord.text = it.satCriticalReadingAvgScore
                binding.mathRecord.text = it.satMathAvgScore
                binding.writingRecord.text = it.satWritingAvgScore
            }
        }

        scoresVM.error.observe(viewLifecycleOwner) {
            if (it) {
                lgd("Error: Scores Not Found!")
                binding.testTakersRecord.apply {
                    text = NOT_FOUND
                    setTextColor(Color.RED)
                }
                binding.criticalReadingRecord.apply {
                    text = NOT_FOUND
                    setTextColor(Color.RED)
                }
                binding.mathRecord.apply {
                    text = NOT_FOUND
                    setTextColor(Color.RED)
                }
                binding.writingRecord.apply {
                    text = NOT_FOUND
                    setTextColor(Color.RED)
                }
            }
        }

        // update score
        scoresVM.getScore()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // handle back pressed function
    fun onBackPressed() {
        lgd("Back button pressed!")
        // update front page
        mainVM.display.postValue(SCHOOLS)

        val fm = activity?.supportFragmentManager
        if (fm?.backStackEntryCount!! > 0) {
            fm.popBackStack()
        } else {
            activity?.onBackPressed()
        }
    }

    companion object {
        private const val ARG_SCHOOL_NUMBER = "school_number"
        private const val ARG_SCHOOL_NAME = "school_name"

        @JvmStatic
        fun newInstance(number: String, name: String) =
            SchoolScoresFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SCHOOL_NUMBER, number)
                    putString(ARG_SCHOOL_NAME, name)
                }
            }
    }
}