package com.hemant_20230509_hemantparmar_nycschools.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
	tableName = "score"
)
data class Score(

	@SerializedName("dbn")
	@PrimaryKey(autoGenerate = false)
	val dbn: String,

	@SerializedName("sat_writing_avg_score")
	val satWritingAvgScore: String,

	@SerializedName("sat_critical_reading_avg_score")
	val satCriticalReadingAvgScore: String,

	@SerializedName("sat_math_avg_score")
	val satMathAvgScore: String,

	@SerializedName("school_name")
	val schoolName: String,

	@SerializedName("num_of_sat_test_takers")
	val numOfSatTestTakers: String
)
