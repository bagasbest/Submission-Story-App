package com.project.dicodingplayground.practice_modul.databaserelation

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.project.dicodingplayground.practice_modul.databaserelation.database.Student
import com.project.dicodingplayground.practice_modul.databaserelation.database.StudentAndUniversity
import com.project.dicodingplayground.practice_modul.databaserelation.database.StudentDao
import com.project.dicodingplayground.practice_modul.databaserelation.database.StudentWithCourse
import com.project.dicodingplayground.practice_modul.databaserelation.database.UniversityAndStudent
import com.project.dicodingplayground.practice_modul.databaserelation.helper.SortType
import com.project.dicodingplayground.practice_modul.databaserelation.helper.SortUtils

class StudentRepository(private val studentDao: StudentDao) {

    //    fun getAllStudent(): LiveData<List<Student>> = studentDao.getAllStudent()
//    fun getAllStudent(sortType: SortType): LiveData<List<Student>> {
//        val query = SortUtils.getSortedQuery(sortType)
//        return studentDao.getAllStudent(query)
//    }
    fun getAllStudent(sortType: SortType): LiveData<PagedList<Student>> {
        val query = SortUtils.getSortedQuery(sortType)
        val student = studentDao.getAllStudent(query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(30)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(student, config).build()
    }

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> =
        studentDao.getAllStudentAndUniversity()

    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> =
        studentDao.getAllUniversityAndStudent()

    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> =
        studentDao.getAllStudentWithCourse()

//    suspend fun insertAllData() {
//        studentDao.insertStudent(InitialDataSource.getStudents())
//        studentDao.insertUniversity(InitialDataSource.getUniversities())
//        studentDao.insertCourse(InitialDataSource.getCourses())
//        studentDao.insertCourseStudentRef(InitialDataSource.getCourseStudentRelation())
//    }

}