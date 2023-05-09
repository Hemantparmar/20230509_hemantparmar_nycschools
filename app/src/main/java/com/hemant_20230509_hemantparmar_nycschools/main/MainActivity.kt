package com.hemant_20230509_hemantparmar_nycschools.main

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.hemant_20230509_hemantparmar_nycschools.helper.lgd
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hemant_20230509_hemantparmar_nycschools.R
import com.hemant_20230509_hemantparmar_nycschools.databinding.ActivityMainBinding
import com.hemant_20230509_hemantparmar_nycschools.helper.msg
import com.hemant_20230509_hemantparmar_nycschools.main.InfoStatus.*
import com.hemant_20230509_hemantparmar_nycschools.main.adapter.SectionsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

private val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_NETWORK_STATE
)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // view binding
    private lateinit var binding: ActivityMainBinding
    // view model
    private val mainVM: MainViewModel by viewModels()

    // app permission
    private val reqMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            lgd("MainAct-Permission: ${it.key} = ${it.value}")
            if (!it.value) {
                // toast
                msg(this, "Permission: ${it.key} denied!", 1)
                finish()
            }
        }
    }

    // titles for tab layout
    private val TAB_TITLES = arrayOf(
        R.string.tab_title_1,
        R.string.tab_title_2,
        R.string.tab_title_3,
        R.string.tab_title_4,
        R.string.tab_title_5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check app permissions
        reqMultiplePermissions.launch(REQUIRED_PERMISSIONS)


        //region TABs: controls schools' categories
        val sectionsPagerAdapter = SectionsPagerAdapter(this, TAB_TITLES.size)
        val viewPager: ViewPager2 = binding.viewPager //findViewById
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs //findViewById
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = "${getString(TAB_TITLES[position])}"
        }.attach()
        //endregion

        // select info fragment
        mainVM.display.observe(this) {
            when (it) {
                SCHOOLS -> {
                    binding.viewPager.visibility = View.VISIBLE
                    binding.scoresDisplay.visibility = View.GONE
                }
                SAT_SCORES -> {
                    binding.viewPager.visibility = View.GONE
                    binding.scoresDisplay.visibility = View.VISIBLE
                }
            }
        }

        mainVM.dbMigrated.observe(this) {
            when (it) {
                1 -> {
                    msg(this, "Downloading...", 1)
                }
                2 -> {
                    msg(this, "Data updated.", 0)
                }
                3 -> {
                    showAlertBox()

                }
            }

        }
    }

    // Alert about data error
    fun showAlertBox() {
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Please check your Internet Connection!" +
                "\nCLOSE?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Exit", DialogInterface.OnClickListener {
                    dialog, id -> finish()
            })
            // negative button text and action
            .setNegativeButton("Try again!", DialogInterface.OnClickListener {
                    dialog, id ->
                run {
                    dialog.cancel()
                    mainVM.downloadData()
                }
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Alert! Data Error...")
        // show alert dialog
        alert.show()
    }

    override fun onBackPressed() {
        // shut off SAT display
        mainVM.updateDisplay()

        // swap tabs
        if (binding.viewPager.currentItem == 0) {
            binding.viewPager.currentItem = TAB_TITLES.size - 1
        } else {
            // Otherwise, select the previous step.
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }
}