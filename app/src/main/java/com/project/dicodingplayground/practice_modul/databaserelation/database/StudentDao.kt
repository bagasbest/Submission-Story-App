package com.project.dicodingplayground.practice_modul.databaserelation.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: List<Student>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUniversity(university: List<University>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: List<Course>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourseStudentRef(courseStudentCrossRef: List<CourseStudentCrossRef>)

    //@Query("SELECT * FROM student")
    @RawQuery(observedEntities = [Student::class])
    //fun getAllStudent(query: SupportSQLiteQuery): LiveData<List<Student>>
    fun getAllStudent(query: SupportSQLiteQuery): DataSource.Factory<Int, Student>

    @Transaction
    @Query("SELECT * From student")
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>>

    @Transaction
    @Query("SELECT * FROM university")
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>>

    @Transaction
    @Query("SELECT * FROM student")
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>>
}