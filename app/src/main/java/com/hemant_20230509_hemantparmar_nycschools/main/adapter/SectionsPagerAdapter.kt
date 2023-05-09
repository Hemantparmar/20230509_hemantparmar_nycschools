package com.hemant_20230509_hemantparmar_nycschools.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hemant_20230509_hemantparmar_nycschools.helper.lgi
import com.hemant_20230509_hemantparmar_nycschools.main.SchoolInfoFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fa: FragmentActivity, val size: Int):
    FragmentStateAdapter(fa) {

    // total tabs
    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        lgi("Create new fragement: ${position+1}")
        return SchoolInfoFragment.newInstance(position + 1)
    }
}