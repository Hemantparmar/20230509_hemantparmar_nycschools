package com.hemant_20230509_hemantparmar_nycschools.network

import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.data.entity.Score
import retrofit2.http.GET

interface ApiService {

    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchoolInfo(): List<School>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSchoolScores(): List<Score>

}