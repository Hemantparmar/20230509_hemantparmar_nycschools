package com.hemant_20230509_hemantparmar_nycschools.main

import androidx.lifecycle.*
import com.hemant_20230509_hemantparmar_nycschools.helper.lgd
import com.hemant_20230509_hemantparmar_nycschools.helper.lgi
import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.data.room.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SchoolInfoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val schoollist = MutableLiveData<List<School>>()

    init {
        updateStatus()
    }

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    @Throws(IOException::class)
    fun updateStatus() {

        viewModelScope.launch(Dispatchers.IO) {
            // if schoolcount < 10 then download
            if (!repository.checkRoomStatus()) {
                val record = repository.saveToRoom()
                lgd("download record --  school: ${record.schoolCount}, " +
                        "sat scores: ${record.scoreCount}")
            }
            else
                lgi("No need to update from web.")

            lgi("school list size: ${schoollist.value?.size}")

            if (schoollist.value?.size == null) {
                val list = repository.getAllSchools()
                list.sortedBy { it.schoolName }
                lgi("list: ${list.first().schoolName} and ${list.last().schoolName}")

                schoollist.postValue(list)
//                lgd("schoollist added: ${schoollist.value!!.size} from list(${list.size})")
            } else {
                lgi("school list is not empty.")
            }

        }
    }
}