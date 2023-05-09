package com.hemant_20230509_hemantparmar_nycschools.network

import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.data.entity.Score

interface ApiHelper {

    suspend fun getSchoolInfo(): List<School>

    suspend fun getSchoolScores(): List<Score>

}