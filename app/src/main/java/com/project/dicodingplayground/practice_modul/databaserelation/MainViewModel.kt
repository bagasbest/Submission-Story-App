package com.project.dicodingplayground.practice_modul.databaserelation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.project.dicodingplayground.practice_modul.databaserelation.database.Student
import com.project.dicodingplayground.practice_modul.databaserelation.database.StudentAndUniversity
import com.project.dicodingplayground.practice_modul.databaserelation.database.StudentWithCourse
import com.project.dicodingplayground.practice_modul.databaserelation.database.UniversityAndStudent
import com.project.dicodingplayground.practice_modul.databaserelation.helper.SortType

class MainViewModel(private val studentRepository: StudentRepository): ViewModel() {
    private val _sort = MutableLiveData<SortType>()


    init {
//        insertAllData()
        _sort.value = SortType.ASCENDING
    }

    fun changeSortType(sortType: SortType) {
        _sort.value = sortType
    }

    //fun getAllStudent(): LiveData<List<Student>> = studentRepository.getAllStudent()
//    fun getAllStudent(): LiveData<List<Student>> = _sort.switchMap {
//        studentRepository.getAllStudent(it)
//    }

    fun getAllStudent(): LiveData<PagedList<Student>> = _sort.switchMap {
        studentRepository.getAllStudent(it)
    }

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentRepository.getAllStudentAndUniversity()
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentRepository.getAllUniversityAndStudent()
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentRepository.getAllStudentWithCourse()
//    private fun insertAllData() = viewModelScope.launch {
//        studentRepository.insertAllData()
//    }

}