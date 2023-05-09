package com.hemant_20230509_hemantparmar_nycschools.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemant_20230509_hemantparmar_nycschools.helper.lgd
import com.hemant_20230509_hemantparmar_nycschools.data.room.Repository
import com.hemant_20230509_hemantparmar_nycschools.helper.MIGRATED
import com.hemant_20230509_hemantparmar_nycschools.main.InfoStatus.SCHOOLS
import com.hemant_20230509_hemantparmar_nycschools.data.storage.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storage: Storage,
    private val repository: Repository
) : ViewModel() {

    // display picker
    val display = MutableLiveData<InfoStatus>()
    // database migration check
    val dbMigrated = MutableLiveData<Int>()

    init {
        display.value = SCHOOLS
        dbMigrated.value = storage.getInt(MIGRATED)

        if (dbMigrated.value == 0) {
            downloadData()
        }
    }

    // update display by back button
    fun updateDisplay() {
        display.postValue(SCHOOLS)
    }

    /*
     Data status --
         0: nothing,
         1: downloading,
         2: done,
         3: error
     */
    fun downloadData() {
        viewModelScope.launch(Dispatchers.IO) {
            dbMigrated.postValue(1) // 1: downloading
            val record = repository.saveToRoom()

            lgd(
                "download record --  school: ${record.schoolCount}, " +
                        "sat scores: ${record.scoreCount}"
            )

            if (record.schoolCount > 300) { // 2: done
                dbMigrated.postValue(2)
                storage.setInt(MIGRATED, 2)
            } else {
                dbMigrated.postValue(3) // 3: error
                storage.setInt(MIGRATED, 3)
            }
        }
    }
}

enum class InfoStatus {
    SCHOOLS, SAT_SCORES
}