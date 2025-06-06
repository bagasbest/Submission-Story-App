package com.project.dicodingplayground.practice_modul.databaserelation.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Student(
    @PrimaryKey
    val studentId: Int,
    val name: String,
    val univId: Int,
    @ColumnInfo(defaultValue = "false")
    val isGraduate: Boolean? = false
)

@Entity
data class University(
    @PrimaryKey
    val universityId: Int,
    val name: String
)

@Entity
data class Course(
    @PrimaryKey
    val courseId: Int,
    val name: String
)

data class StudentAndUniversity(
    @Embedded
    val student: Student,

    @Relation(
        parentColumn = "univId",
        entityColumn = "universityId"
    )
    val university: University? = null
)

data class UniversityAndStudent(
    @Embedded
    val university: University,

    @Relation(
        parentColumn = "universityId",
        entityColumn = "univId"
    )
    val student: List<Student>
)

@Entity(primaryKeys = ["sId", "cId"])
data class CourseStudentCrossRef(
    val sId: Int,
    @ColumnInfo(index = true)
    val cId: Int
)

data class StudentWithCourse(
    @Embedded
    val studentAndUniversity: StudentAndUniversity,

    @Relation(
        parentColumn = "studentId",
        entity = Course::class,
        entityColumn = "courseId",
        associateBy = Junction(
            value = CourseStudentCrossRef::class,
            parentColumn = "sId",
            entityColumn = "cId"
        )
    )
    val course: List<Course>

)