package com.hemant_20230509_hemantparmar_nycschools.network

import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.data.entity.Score
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {

    override
    suspend fun getSchoolInfo(): List<School> =
        apiService.getSchoolInfo()


    override
    suspend fun getSchoolScores(): List<Score> =
        apiService.getSchoolScores()


}