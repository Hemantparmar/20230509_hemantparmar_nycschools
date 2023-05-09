package com.hemant_20230509_hemantparmar_nycschools.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.data.entity.Score
import com.hemant_20230509_hemantparmar_nycschools.data.room.dao.SchoolDao
import com.hemant_20230509_hemantparmar_nycschools.data.room.dao.ScoreDao
import com.hemant_20230509_hemantparmar_nycschools.data.room.migration.Migrate_1_2
import com.hemant_20230509_hemantparmar_nycschools.helper.DB_NAME


/* School Database has two tables:
    table--school: Holds basic information
    table--score: Holds average scores about the school
*/
@Database(
    entities = [School::class, Score::class],
    version = 2     //school table update
)
abstract class SchoolDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolDatabase? = null

        fun getDatabase(context: Context): SchoolDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SchoolDatabase::class.java,
                    DB_NAME
                )
                .addMigrations(Migrate_1_2) //school table update
                .fallbackToDestructiveMigration().build()

                INSTANCE = instance
                instance
            }
        }
    }
}