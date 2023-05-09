package com.hemant_20230509_hemantparmar_nycschools.data.room.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/*
    Version 1: dbn(school id), school_name, overview_paragraph
	version 2
	        Add new columns:
            location        TEXT
            city            TEXT
            stateCode       TEXT
            zip        		TEXT
            phoneNumber     TEXT
            faxNumber       TEXT
            schoolEmail     TEXT
            website			TEXT

 */
val Migrate_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE school ADD COLUMN location TEXT")
        database.execSQL("ALTER TABLE school ADD COLUMN city TEXT")
        database.execSQL("ALTER TABLE school ADD COLUMN state_code TEXT")
        database.execSQL("ALTER TABLE school ADD COLUMN zip TEXT")
        database.execSQL("ALTER TABLE school ADD COLUMN phone_number TEXT")
        database.execSQL("ALTER TABLE school ADD COLUMN fax_number TEXT")
        database.execSQL("ALTER TABLE school ADD COLUMN school_email TEXT")
        database.execSQL("ALTER TABLE school ADD COLUMN website TEXT")
    }
}
