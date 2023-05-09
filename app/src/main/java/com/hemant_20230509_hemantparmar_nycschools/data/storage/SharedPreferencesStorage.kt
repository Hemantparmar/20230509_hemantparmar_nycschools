/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hemant_20230509_hemantparmar_nycschools.data.storage

import android.content.Context
import com.hemant_20230509_hemantparmar_nycschools.helper.ERROR
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// @Inject tells Dagger how to provide instances of this type
class SharedPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context
) : Storage {

    private val fileName = "storage_03212021"

    private val sharedPreferences = context
        .getSharedPreferences(fileName, Context.MODE_PRIVATE)

    // String
    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, ERROR)!!
    }

    // Boolean
    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)!!
    }

    // Integer
    override fun setInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)!!
    }

    // Other
    override fun delKey(key: String): Boolean {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
        return !sharedPreferences.contains(key)
    }

    override fun clear() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}
