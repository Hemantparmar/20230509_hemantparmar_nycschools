package com.hemant_20230509_hemantparmar_nycschools.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
	tableName = "school"
)
data class School(
	// version 1
	@SerializedName("dbn")
	@PrimaryKey(autoGenerate = false)
	val dbn: String,

	@SerializedName("school_name")
	@ColumnInfo(name = "school_name")
	val schoolName: String,

	@SerializedName("overview_paragraph")
	@ColumnInfo(name = "overview_paragraph")
	val overviewParagraph: String,

	/*
	version 2
	        Add new columns:
            location        String
            city            String
            state_code      String
            zip        		String
            phone_number    String
            fax_number      String
            school_email    String
            website			String

	 */
	// address
	@SerializedName("location")
	@ColumnInfo(name = "location")
	val location: String?,

	// city
	@SerializedName("city")
	@ColumnInfo(name = "city")
	val city: String?,

	// state
	@SerializedName("state_code")
	@ColumnInfo(name = "state_code")
	val stateCode: String?,

	// zipcode
	@SerializedName("zip")
	@ColumnInfo(name = "zip")
	val zip: String?,

	// phone#
	@SerializedName("phone_number")
	@ColumnInfo(name = "phone_number")
	val phoneNumber: String?,

	// fax#
	@SerializedName("fax_number")
	@ColumnInfo(name = "fax_number")
	val faxNumber: String?,

	// school email
	@SerializedName("school_email")
	@ColumnInfo(name = "school_email")
	val schoolEmail: String?,

	// website
	@SerializedName("website")
	@ColumnInfo(name = "website")
	val website: String?

)
