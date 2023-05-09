package com.hemant_20230509_hemantparmar_nycschools.main

import androidx.lifecycle.*
import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.data.entity.Score
import com.hemant_20230509_hemantparmar_nycschools.data.room.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolScoresViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // school scores
    val scores = MutableLiveData<Score?>()
    val school = MutableLiveData<School?>()
    val error = MutableLiveData<Boolean>()
    var _key = ""

    init {
        scores.value = null
        error.value = false
    }

    // store key for searching
    fun setKey(key: String) {
        _key = key
    }

    // search SAT scores
    fun getScore() {
        if (_key != "") {
            viewModelScope.launch(Dispatchers.IO) {
                val mSchool = repository.getSchool(_key)
                if (mSchool != null) {
                    school.postValue(mSchool)
                }

                val mScore = repository.getScores(_key)
                if (mScore != null)
                    scores.postValue(mScore!!)
                else
                    error.postValue(true)
            }
        }
    }

}