package com.hemant_20230509_hemantparmar_nycschools.main.adapter

import com.hemant_20230509_hemantparmar_nycschools.data.entity.School

interface SchoolClickListener {
    fun onSchool_item_click(key: School)
}