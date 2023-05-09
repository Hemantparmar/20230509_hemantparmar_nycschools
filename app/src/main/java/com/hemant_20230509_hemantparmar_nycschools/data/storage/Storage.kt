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

interface Storage {
    // String
    fun setString(key: String, value: String)
    fun getString(key: String): String

    // Boolean
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean

    // Int
    fun setInt(key: String, value: Int)
    fun getInt(key: String): Int


    fun delKey(key: String): Boolean
    fun clear()
}
