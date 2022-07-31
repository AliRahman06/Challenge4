package com.binar.challenge4.data

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.binar.challenge4.data.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM Student ORDER BY judul ASC")
    fun getAllStudent(): List<Student>

    @Query("SELECT * FROM Student ORDER BY judul DESC")
    fun getAllDataDesc(): List<Student>

    @Insert(onConflict = REPLACE)
    fun insertStudent(student: Student):Long

    @Update
    fun updateStudent(student: Student):Int

    @Delete
    fun deleteStudent(student: Student):Int
}