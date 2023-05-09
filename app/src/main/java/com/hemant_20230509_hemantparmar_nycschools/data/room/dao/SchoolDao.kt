package com.hemant_20230509_hemantparmar_nycschools.data.room.dao

import androidx.room.*
import com.hemant_20230509_hemantparmar_nycschools.data.entity.School

@Dao
interface SchoolDao {
    // Get
    @Query("SELECT * FROM school WHERE dbn = :mDbn")
    suspend fun getSchool(mDbn: String): School?

    @Query("SELECT * FROM school")
    suspend fun getSchools(): List<School>

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schoolList: List<School>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(school: School)

    // Delete
    @Delete
    suspend fun delete(school: School)

    @Query("DELETE FROM school")
    suspend fun deleteAll()

    // count all rows
    @Query("SELECT COUNT(dbn) FROM school")
    suspend fun getCount(): Int
}